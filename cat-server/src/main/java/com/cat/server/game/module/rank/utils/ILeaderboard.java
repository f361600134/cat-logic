package com.cat.server.game.module.rank.utils;

import java.util.Collection;
import java.util.Map;

public interface ILeaderboard<K, V>{
	
	/**
	 * 添加排行榜元素,被修改,移除掉的数据会通过回调通知业务层
	 * @return
	 */
	public V put(K k, V v) throws Exception;
	
	/**
	 * 添加排行榜元素,被修改,移除掉的数据会通过回调通知业务层
	 * @return
	 */
	public void putAll(Map<? extends K, ? extends V> m) throws Exception;
	
	/**
	 * 移除排行榜元素,被移除掉的数据会通过回调通知业务层
	 * @param k
	 */
	public void remove(K key) throws Exception;
	
	/**
	 * 获取key获取到名次
	 * @param K 指定key
	 * @return Integer  名次
	 */
	public Integer getRankByKey(K key) throws Exception;
	
	/**
	 *      获取指定对象的排名
	 * @param v
	 * @return  
	 * @return Integer  
	 */
	public Integer getRank(V v) throws Exception;
	
	/**
	 *    获取排名第一的数据
	 * @return V  
	 */
	public V getFirst() throws Exception;
	
	/**
	 * 获取最后一名的数据
	 * @return V  
	 * @date 2019年3月25日下午2:27:26
	 */
	public V getLast() throws Exception;
	
	/**
	 * 根据指定排名获取数据
	 * @param rank 名次
	 * @return V  
	 * @date 2019年3月25日下午2:28:59
	 */
	public V getByRank(int rank) throws Exception;

	/**
	 * 获取指定排名区间内的排名数据
	 * @param fromRank 起始排名
	 * @param toRank 结束排名
	 */
	public Collection<V> subRankInfo(int fromRank, int toRank) throws Exception;

	/**
	 * 获取指定排名区间内的排名数据, 包含指定的头和尾
	 * @param from 开始对象
	 * @param to 结束对象
	 */
	public Collection<V> subRankInfo(V from, V to) throws Exception;
	
	/**
	 * 获取指定排名区间内的排名数据,  需要指定是否包含起始值
	 * @param from 开始对象
	 * @param fromInclusive 是否包含开始对象
	 * @param to 结束对象
	 * @param toInclusive 是否包含结束对象
	 */
	public Collection<V> subRankInfo(V from, boolean fromInclusive, V to, boolean toInclusive) throws Exception;

	/**
	 * 获取排行榜快照
	 * @throws Exception 
	 */
	public Collection<V> getRankInfo() throws Exception;
	
	/**
	 * 返回排行榜内所有数据
	 * @return
	 */
	public Collection<V> values() throws Exception;
	
	/**
	 * 清空排行榜
	 */
	public void clear() throws Exception;
	
	/**
	 * 清空排行榜
	 */
	public int size() throws Exception;

}
