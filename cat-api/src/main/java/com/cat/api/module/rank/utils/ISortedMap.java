package com.cat.api.module.rank.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

/**
 * 	有序Map接口,
 * @author Jeremy Feng
 */
public interface ISortedMap<K, V> extends Map<K, V>{
	
	/**
	 * 获取第一个元素
	 * @return 第一个元素, 如果只有1个元素,则返回当前元素
	 */
	public V first();
	
	/**
	 * 获取最后一个元素
	 * @return 最后的那个元素, 如果只有1个元素, 则返回当前元素
	 */
	public V last();
	
	/**
	 * 	从头部向后截取数据
	 * @param v 开始对象
	 * @param inclusive 是否包含开始对象
	 * @return 返回一个视图
	 */
	public SortedSet<V> headSet(V v, boolean inclusive);
	
	/**
	 * 	从尾部向前截取数据
	 * @param v 尾部对象
	 * @return 是否包含尾部对象
	 * @return 返回一个视图
	 */
	public SortedSet<V> tailSet(V v, boolean inclusive);
	
	/**
	 * 	截取数据, 默认包含起始值
	 * @param fv 开始对象
	 * @param tv 结束对象
	 * @return 截取内容,返回一个视图
	 */
	public SortedSet<V> subSet(V fv, V tv);
	
	/**
	 * 截取数据, 需指定起始值,结束值
	 * @param fv 开始对象
	 * @param incloudFrom 是否包含开始对象
	 * @param tv 结束对象
	 * @param incloudTo 是否包含结束对象
	 * @return 截取内容,返回一个视图
	 */
	public SortedSet<V> subSet(V fv, boolean incloudFrom, V tv, boolean incloudTo);
	
	/**
	 * 通过V移除掉值
	 * @param v 被移除的对象.
	 */
	public void removeValue(V v);
	
	/**
	 * 	通过V反向映射K
	 * @param v 值
	 */
	public K getKey(V v);
	
	 /**
     * @return an iterator over the elements in this set
     */
    Iterator<V> iterator();
	
    /**
     * @return an array containing all the elements in this set
     */
    Object[] toArray();
}
