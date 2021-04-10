package com.cat.server.game.module.rank.utils;

import java.util.Collection;
import java.util.Map;

public interface ILeaderboard<K, V>{
	
	/**
	 * 初始化排行榜数据,第一次加入排行榜,不监听移除.
	 *   
	 * @return void  
	 * @date 2021年3月21日下午9:01:31
	 */
	public void initData(Map<? extends K, ? extends V> m);
	
	/**
	 * 添加排行榜元素,被修改,移除掉的数据会通过回调通知业务层
	 * @return
	 * @throws Exception 
	 */
	public V put(K k, V v) throws Exception;
	
	/**
	 * 添加排行榜元素,被修改,移除掉的数据会通过回调通知业务层
	 * @return
	 */
	public void putAll(Map<? extends K, ? extends V> m);
	
	/**
	 * 移除排行榜元素,被移除掉的数据会通过回调通知业务层
	 * @param k
	 */
	public void remove(K key);
	
	/**
	 * 获取key获取到名词
	 * @param v
	 * @return  
	 * @return Integer  
	 * @throws Exception 
	 * @date 2019年3月25日下午2:27:52
	 */
	public Integer getRankByKey(K key) throws Exception;
	
	/**
	 * 获取排名
	 * @param v
	 * @return  
	 * @return Integer  
	 * @throws Exception 
	 * @date 2019年3月25日下午2:27:52
	 */
	public Integer getRank(V v) throws Exception;
	
	/**
	 * 获取排名第一的数据
	 * @return  
	 * @return V  
	 * @throws Exception 为空的时候会爆异常
	 * @date 2019年3月25日下午2:27:26
	 */
	public V getFirst() throws Exception;
	
	/**
	 * 获取最后一名的数据
	 * @return  
	 * @return V  
	 * @throws Exception 
	 * @date 2019年3月25日下午2:27:26
	 */
	public V getLast() throws Exception;
	
	/**
	 * 根据指定排名获取数据
	 * @param rank 名次
	 * @return  
	 * @return V  
	 * @throws Exception 
	 * @date 2019年3月25日下午2:28:59
	 */
	public V getByRank(int rank) throws Exception;

	/**
	 * 获取指定排名区间内的排名数据
	 * @param fromRank 起始排名
	 * @param toIndex 结束排名
	 * @return
	 * @return Collection<V>
	 * @throws Exception 
	 * @date 2019年3月25日下午2:51:37
	 */
	public Collection<V> subRankInfo(int fromRank, int toRank) throws Exception;

	/**
	 * 获取指定排名区间内的排名数据
	 * @param fromIndex
	 * @param toIndex
	 * @return  
	 * @return Collection<V>  
	 * @throws Exception 
	 * @date 2019年3月25日下午2:51:37
	 */
	public Collection<V> subRankInfo(V from, V to) throws Exception;

	/**
	 * 获取排行榜快照
	 * @throws Exception 
	 */
	public Collection<V> getRankInfo() throws Exception;


}
