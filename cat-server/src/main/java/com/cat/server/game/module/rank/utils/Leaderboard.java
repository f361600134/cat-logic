package com.cat.server.game.module.rank.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

import com.google.common.collect.Lists;

/**
 * @author Jeremy Feng
 * @changhe 新增Leaderboard类, 再一次的对SortedValueMap进行封装<br>
 * 主要改动: 更加细粒度的提取公共元素,一次写成工具, 到处使用
 * @warning 线程不安全
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
	/**
	 * 被修改的列表
	 */
	private Collection<V> updateSet = Lists.newArrayList();
	private Collection<V> deleteSet = Lists.newArrayList();
	
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
		this.maximum = maximum;
		this.listen = (ICallBackListener<V>)NullListener.INSTANCE;
	}
	
	public Leaderboard(Comparator<V> comparator, int maximum, ICallBackListener<V> listen){
		this.map = new SortedValueMap<K, V>(comparator);
		this.comparator = comparator;
		this.maximum = maximum;
		setListen(listen);
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
	
//	/**
//	 * 溢出则移除,小量数据适用. 效率会随着数据量的增长而下降
//	 * @param v 被移除掉的数据
//	 */
//	private Collection<V> removeIfoverflow(){
//		final int size = map.size();
//		if (maximum > DEFAULT_CAPACITY &&  size > maximum) {
//			int count = size - maximum;
//			for (int i = 0; i < count; i++) {
//				V v = map.last();
//				map.removeValue(v);
//				return v;
//			}
//		}
//		return null;
//	}
	
	/**
	 * 溢出则移除, 适用于多条溢出数据进行移除
	 * @change 重写方法. 效率比之前removeIfoverflow好太多!
	 * 获取到指定排名对象, 截取对象之前的所有数据.复制出来.
	 * @note 为了节省内存空间
	 * 1.复制数据的时候, 对数据量小的那部分进行复制.
	 * 2.移除数据的时候, 对原数据进行保留
	 * @return void  
	 */
	private Collection<V> removeOverflow(){
		Collection<V> deletes = Lists.newArrayList();
		final int size = map.size();
		if (maximum > DEFAULT_CAPACITY && size > maximum) {
			V max = getByRank(maximum);
			int diff = size - maximum;
			if (diff > maximum) {
				Collection<V> irs = map.tailSet(max);
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
				deletes.addAll(map.headSet(max));//被丢弃的数据,添加进去
			}else{
				//	复制一需要删除的那部分数据
				Collection<V> rs = new ArrayList<V>(subRankInfo(max, map.last()));
				for (V v : rs) {
					K k = map.getKey(v);
					remove(k);
				}
				deletes.addAll(rs);
			}
		}
		return deletes;
	}

	/**
	 * 获取排名
	 */
	@Override
	public Integer getRank(V v) {
		SortedSet<V> sub = map.headSet(v);
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
		Collection<V> vs = subRank(rank, rank);
		if (vs.iterator().hasNext()) {
			v = vs.iterator().next();
		}
		return v;
	}

	@Override
	public Collection<V> subRankInfo(int fromRank, int toRank) {
		return subRank2(fromRank, toRank);
	}

	@Override
	public Collection<V> subRankInfo(V from, V to) {
		return map.subSet(from, to);
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
	 * 效率略高
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()){
			V v = map.put(entry.getKey(), entry.getValue());
			if (v != null) updateSet.add(v);
		}
		//	批量移除数据
		removeOverflow();
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
	 * 排行榜数据入库
	 * @param key
	 * @param v
	 */
	private void putVal(K key, V v) {
		map.put(key, v);
		deleteSet = removeOverflow();
		//变动数据加入集合
		updateSet.add(v);
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
	
	/**
	 * 返回截取的指定开始到指定结束的数据,足则返回,不足则返回全部.
	 * @param fromIndex 开始名次
	 * @param toIndex 截止名次, 包含次名次
	 * @return
	 */
	public Collection<V> subRank(int fromIndex, int toIndex){
		try {
			Collection<V> result = new ArrayList<>();
			V v;
			int rank = 1;
			Iterator<V> it = map.iterator();
			while(it.hasNext()){
				v = it.next();
				if(fromIndex <= rank && rank <= toIndex){//已到达开始名次
					result.add(v);
					rank ++;
				}else if(rank < fromIndex) {//排名到达开始的名次
					rank ++;
				}
				else {
					break;
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 返回截取的指定开始到指定结束的数据,足则返回,不足则返回全部.
	 * @param fromIndex 开始名次
	 * @param toIndex 截止名次, 包含此名次
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<V> subRank2(int fromRank, int toRank){
		try {
			Collection<V> result = new ArrayList<>();
			Object[] obj = map.toArray();
			//下标从0开始,名次-1
			for (int rank = fromRank; rank <= toRank && rank <= obj.length; rank++) {
				result.add((V)obj[rank-1]);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void remove(K key) {
		V v = map.remove(key);
		deleteSet.add(v);
		callBack();
	}
	
	@Override
	public String toString() {
		return "SortedValueMap:"+map;
	}

	@Override
	public Integer getRankByKey(K key) throws Exception {
		V v = map.get(key);
		return getRank(v);
	}

	@Override
	public void initData(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}
	
	
	
 }
