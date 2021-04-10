package com.cat.server.game.module.rank.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

/**
 * 	有序Map接口
 * @author Jeremy
 */
public interface ISortedMap<K, V> extends Map<K, V>{
	
	/**
	 * 获取第一个元素
	 * @return
	 */
	public V first();
	
	/**
	 * 获取最后一个元素
	 * @return
	 */
	public V last();
	
	/**
	 * 	从头部向后截取数据
	 * @param v
	 * @return
	 */
	public SortedSet<V> headSet(V v);
	
	/**
	 * 	从尾端向前截取数据
	 * @param v
	 * @return
	 */
	public SortedSet<V> tailSet(V v);
	
	/**
	 * 	截取数据, 需指定起始值,结束值
	 * @param fv
	 * @param tv
	 * @return
	 */
	public SortedSet<V> subSet(V fv, V tv);
	
	/**
	 * 通过V移除掉值
	 * @param v
	 */
	public void removeValue(V v);
	
	/**
	 * 	通过V反向隐射K
	 * @param v
	 * @return
	 */
	public K getKey(V v);
	
	 /**
     * Returns an iterator over the elements in this set.  The elements are
     * returned in no particular order (unless this set is an instance of some
     * class that provides a guarantee).
     *
     * @return an iterator over the elements in this set
     */
    Iterator<V> iterator();
	
    /**
     * Returns an array containing all of the elements in this set.
     * If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the
     * elements in the same order.
     *
     * <p>The returned array will be "safe" in that no references to it
     * are maintained by this set.  (In other words, this method must
     * allocate a new array even if this set is backed by an array).
     * The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all the elements in this set
     */
    Object[] toArray();
}
