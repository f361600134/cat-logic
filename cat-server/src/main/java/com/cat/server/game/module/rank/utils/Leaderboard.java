package com.cat.server.game.module.rank.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

/**
 * 排行榜对象
 * @author Jeremy Feng
 */
public class Leaderboard<K, V> implements ILeaderboard<K, V> {
	
	/*** 默认初始容量,-1表示无限制*/
    private static final int DEFAULT_CAPACITY = -1;
	
	/**
     * 最大容量,-1表示不限制
     * 超过最大量的数据会被移除掉
     */
	private int maximum;
	
	/** 
	 * 缓存后的排序数据,有序集
	 **/
	private SortedValueMap<K, V> map;
	
	/** 
	 * 比较器
	 * */
	private Comparator<V> comparator;
	
	/**
	 * 回调监听,当有数据变动时直接调用监听器内的call方法
	 */
	private ICallBackListener<V> listen;
	/*** 被修改的列表, 原榜内数据变动,视为被修改数据 */
	private Collection<V> updateSet = new ArrayList<>();
	/*** 被删除的列表, 从榜内移除掉的数据, 视为删除列表 */
	private Collection<V> deleteSet = new ArrayList<>();
	
	enum NullListener implements ICallBackListener<Object> {
	   INSTANCE;
	   @Override
	   public void call(Collection<Object> updateSet, Collection<Object> deleteSet) {}
	}
	
	@SuppressWarnings("unchecked")
	public Leaderboard(Comparator<V> comparator){
		this.maximum = DEFAULT_CAPACITY;
		this.map = new SortedValueMap<K, V>(comparator);
		this.comparator = comparator;
		this.listen = (ICallBackListener<V>)NullListener.INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public Leaderboard(Comparator<V> comparator, int maximum){
		this.map = new SortedValueMap<K, V>(comparator);
		this.comparator = comparator;
		this.setMaximum(maximum);
		this.listen = (ICallBackListener<V>)NullListener.INSTANCE;
	}
	
	public Leaderboard(Comparator<V> comparator, int maximum, ICallBackListener<V> listen){
		this.map = new SortedValueMap<K, V>(comparator);
		this.comparator = comparator;
		this.setMaximum(maximum);
		this.setListen(listen);
	}
	
	/**
	 * 设置排行榜最大量
	 * @param maximum
	 */
	public void setMaximum(int maximum) {
		if (maximum == 0) {
			throw new IllegalArgumentException("the input parameter {maximum} is not allowed be zero");
		}
		this.maximum = maximum;
	}
	
	/**
	 * 设置排行榜监听器
	 * @param maximum
	 */
	public void setListen(ICallBackListener<V> listen) {
		if (listen == null) {
			throw new NullPointerException("the listen for Leaderboard can not be null");
		}
		this.listen = listen;
	}
	
	/**
	 * 溢出则移除, 适用于多条溢出数据进行移除<br>
	 * @change 重写方法. 效率比之前removeIfoverflow好太多! 获取到指定排名对象, 截取对象之前的所有数据.复制出来.<br>
	 * @note 为了节省内存空间<br>
	 * 1.复制数据的时候, 对数据量小的那部分进行复制.<br>
	 * 2.移除数据的时候, 对原数据进行保留<br>
	 */
	private Collection<V> removeOverflow(){
		Collection<V> deletes = new ArrayList<>();
		final int size = map.size();
		if (maximum > DEFAULT_CAPACITY && size > maximum) {
			V max = getByRank(maximum);
			int diff = size - maximum;
			if (diff > maximum) {
				//保留部分
				Collection<V> irs = map.headSet(max, true);
				//删除部分
				deletes.addAll(map.tailSet(max, false));//被删除的数据,添加进去
				//	实例化一个新的map.把需要的数据添加进去
				//	截取的数据量肯定是小于指定数量的
				SortedValueMap<K, V> temMap = new SortedValueMap<K, V>(comparator);
				for (V v : irs) {
					K k = map.getKey(v);
					if (k != null) {
						temMap.put(k, v);
					}
				}
				this.map = temMap;
			}else{
				//	复制一需要删除的那部分数据
				Collection<V> rs = new ArrayList<V>(subRankInfo(max, false, map.last(), true));
				for (V v : rs) {
					K k = map.getKey(v);
					this.removeVal(k);
				}
				deletes.addAll(rs);
			}
		}
		return deletes;
	}

	/**
	 * 获取排名, 
	 */
	@Override
	public Integer getRank(V v) {
		SortedSet<V> sub = map.headSet(v, true);
		if(sub==null)	return 0;
		return sub.size() + 1;
	}

	@Override
	public V getFirst() {
		if (map.isEmpty()) {
			return null;
		}
		return map.first();
	}

	@Override
	public V getLast() {
		if (map.isEmpty()) {
			return null;
		}
		return map.last();
	}

	@Override
	public V getByRank(int rank) {
		V v = null;
		Collection<V> vs = subRankInfo(rank, rank);
		Iterator<V> iter = vs.iterator();
		if (iter.hasNext()) {
			v = iter.next();
		}
		return v;
	}

	
	/**
	 * 返回截取的指定开始到指定结束的数据,足则返回,不足则返回全部.第二種寫法
	 * @param fromIndex 开始名次, 最小为1, 表示从第一名开始截取
	 * @param toIndex 截止名次, 包含此名次
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> subRankInfo(int fromRank, int toRank) {
		if (fromRank < 1) {
            throw new IndexOutOfBoundsException("Leaderboard's size out of range"+fromRank);
        }
        if (toRank > map.size()) {
            throw new IndexOutOfBoundsException("Leaderboard's size out of range"+toRank);
        }
        int subLen = toRank - fromRank;
        if (subLen < 0) {
        	throw new IndexOutOfBoundsException("Leaderboard's size out of range"+subLen);
		}
		Collection<V> result = new ArrayList<>();
		Object[] obj = map.toArray();
		//下标从0开始,名次-1
		for (int rank = fromRank; rank <= toRank && rank <= obj.length; rank++) {
			result.add((V)obj[rank-1]);
		}
		return result;
	}

	@Override
	public Collection<V> subRankInfo(V from, V to) {
		return map.subSet(from, true, to, true);
	}
	
	@Override
	public Collection<V> subRankInfo(V from, boolean fromInclusive, V to, boolean toInclusive) {
		return map.subSet(from, fromInclusive, to, toInclusive);
	}

	@Override
	public Collection<V> getRankInfo() {
		return map.values();
	}
	
	/**
	 * put, 插入数据, true 返回原值, false 返回null
	 */
	@Override
	public V put(K key, V value) {
		boolean bool = putIfAbove(key, value);
		return bool ? value : null;
	}
	
	/**
	 * 先插入所有数据, 进行排序.然后再移除溢出部分.
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if (m == null) {
			throw new NullPointerException("putAll error, value is null");
		}
		for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()){
			V v = map.put(entry.getKey(), entry.getValue());
			if (v != null) updateSet.add(v);
		}
		//	批量移除数据
		this.deleteSet.addAll(removeOverflow());
		//	处理数据
		callBack();
	}
	
	/**
	 * 对put 插入进行优化,插入之前利用比较器进行比较,如果符合条件进行插入,否则直接跳过
	 * 条件: 1. 如果排行榜不满, 直接加入排行榜
	 * 2. 如果排行榜满了, 则判定是否是否可以加入排行榜
	 * 
	 * 优化后的putIfAbove在数据上万之后,性能提升明显,10000条数据效率是put的十倍左右.
	 * 1.有指定排行长度.
	 * 2.新值大于榜内最小值.
	 * @return true:插入成功. false: 插入失败
	 */
	private boolean putIfAbove(K key, V v){
		if (key == null) {
			throw new NullPointerException("putIfAbove error, key is null");
		}
		if (v == null) {
			throw new NullPointerException("putIfAbove error, v is null");
		}
		//如果排行榜满了,插入新数据需要判断是否大于榜内最小值
		if (maximum > DEFAULT_CAPACITY && maximum <= map.size()) {
			int result = comparator.compare(map.last(), v);
			if (result > 0) {
				putVal(key, v);
				return true;
			}
		}else if(maximum > DEFAULT_CAPACITY && maximum > map.size()){
			putVal(key, v);
			return true;
		}
		return false;
	}
	
	/**
	 * 插入排行榜数据
	 * @param key
	 * @param v
	 */
	private void putVal(K key, V v) {
		V old = map.put(key, v);
		if (old != null) {
			//变动数据加入集合
			updateSet.add(v);
		}
		deleteSet.addAll(removeOverflow());
		//调用监听
		callBack();
	}
	
	/**
	 * 回调后清除内容
	 *   
	 * @return void  
	 * @date 2021年3月21日下午10:57:19
	 */
	private void callBack() {
		listen.call(updateSet, deleteSet);
		//完事后清掉集合
		updateSet.clear();
		deleteSet.clear();
	}
	
	@Override
	public void remove(K key) {
		V v = removeVal(key);
		deleteSet.add(v);
		callBack();
	}
	
	/**
	 * 移除排行榜数据
	 * @param key
	 * @param v
	 */
	private V removeVal(K key) {
		return map.remove(key);
	}
	
	@Override
	public String toString() {
		return "SortedValueMap:"+map;
	}

	@Override
	public Integer getRankByKey(K key) {
		V v = map.get(key);
		if (v == null) {
			return 0;
		}
		return getRank(v);
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public int size() {
		return map.size();
	}
	
 }
