package com.cat.api.module.rank.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.*;

/**
 * 根据比较器排序的Map<br>
 * 封装TreeSet, BiMap两个数据结构, TreeSet是一个有序集, 用作处理数据排序, 但是不能快速定位到数据, 使用Bimap作为辅助组成KV格式的有序结构.<br>
 * 所有对外的数据判断, 使用Bimap去做判断处理,. 所有对外返回的数据集合, 则使用TreeSet的数据.
 * @warn 线程不安全, 在并发环境下若没有保证线程安全, 会引发灾难, 常见就是破坏掉TreeSet内部的树结构. 导致数据丢失.<br>
 * @warn 在任何时候, bimap和sortedSet数量都是保持一致且内部对象是同一个引用,
 * @author Jeremy Feng.
 */
public class SortedValueMap<K, V> implements ISortedMap<K, V>{
	
	/**
	 * 	双向map, key, value必须唯一
	 */
	private BiMap<K, V> biMap;
	
	/**
	 * 	有序的set集
	 */
    private TreeSet<V> sortedSet; 
    
    /**
     * @param maximum 最大数量
     * @param comparator 比较器, 比较器返回值不允许为0, 否则会识别成同一个对象, 只保存一个,
     */
    public SortedValueMap(int maximum, Comparator<? super V> comparator){
    	if (maximum <= 0) {
    		throw new IllegalArgumentException("the input parameter {maximum} is not allowed less than zero");
		}
		this.biMap = HashBiMap.create();
    	this.sortedSet = new TreeSet<V>(comparator);
    }
    
    /***
     * @param comparator 比较器, 比较器返回值不允许为0, 否则会识别成同一个对象, 只保存一个,
     */
	public SortedValueMap(Comparator<? super V> comparator){
		this.biMap = HashBiMap.create();
    	this.sortedSet = new TreeSet<V>(comparator);
    }
	
	@Override
	public int size() {
		return biMap.size();
	}

	@Override
	public boolean isEmpty() {
		return biMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return biMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return biMap.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return biMap.get(key);
	}

	/**
	 * 原先分两步:<br>
	 * 1. remove(key), 先根据key移除掉当前数据<br>
	 * 2. put and add, 把新数据丢进排行榜<br>
	 * 这样会导致命名没有变化的内容, 也会重新出榜,入榜一次
	 * 优化方式:
	 * 1. 校验当前数据有无变化, 无变化则跳过, 有变化则执行1,2步骤
	 */
	@Override
	public V put(K key, V value) {
		//Unknow whether the value of v is null.
		V v = biMap.get(key);
		if (value.equals(v)) {
			return null;//Need overwrite hashcode and equals method.
		}
		V old = remove(key);
		sortedSet.add(value);
		biMap.put(key, value);
		return old;
	}
	
	@Override
	public V remove(Object key) {
		V obj = biMap.remove(key);
		if(obj != null){
			this.removeFromSortedSet(obj);
		}
		return obj;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for(Entry<? extends K, ? extends V> entry : m.entrySet()){
			put(entry.getKey(),entry.getValue());
		}
	}

	@Override
	public void clear() {
		biMap.clear();
		sortedSet.clear();
	}

	@Override
	public Set<K> keySet() {
		return biMap.keySet();
	}

	@Override
	public Collection<V> values() {
		return sortedSet;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return biMap.entrySet();
	}

	@Override
	public V last(){
		return sortedSet.last();
	}
	
	@Override
	public V first(){
		return sortedSet.first();
	}
	
	@Override
	public SortedSet<V> headSet(V v, boolean inclusive){
		return sortedSet.headSet(v, inclusive);
	}
	
	@Override
	public SortedSet<V> tailSet(V v, boolean inclusive){
		return sortedSet.tailSet(v, inclusive);
	}
	
	@Override
	public SortedSet<V> subSet(V fv, V tv){
		return sortedSet.subSet(fv, true, tv, true);
	}
	
	@Override
	public SortedSet<V> subSet(V fv, boolean incloudFrom, V tv, boolean incloudTo){
		return sortedSet.subSet(fv, incloudFrom, tv, incloudTo);
	}
	

	@Override
	public Iterator<V> iterator() {
		return sortedSet.iterator();
	}
	

	@Override
	public Object[] toArray() {
		return sortedSet.toArray();
	}
	
	@Override
	public void removeValue(V v) {
		Object obj = biMap.inverse().remove(v);
		if(obj != null){
			this.removeFromSortedSet(v);
		}
	}

	@Override
	public K getKey(V v) {
		return biMap.inverse().get(v);
	}
	
	@Override
	public String toString() {
		return "SortedValueMap [size="+biMap.size()+", biMap=" + biMap + "\nsize="+sortedSet.size()+", sortedSet=" + sortedSet + "]";
	}

	private void removeFromSortedSet(V v) {
		/*
		 * 用sortedSet.remove如果值一致,但引用不一样, 会导致明明是同一个对象,但是无法删除成功,
		 * 所以改用该方法. 先调用sortedset内置方法, 如果移除不成功, 再遍历尝试删除
		 */
		boolean bool = sortedSet.remove(v);
		if (!bool) {
			Iterator<V> it = sortedSet.iterator();
			V temp;
			while (it.hasNext()) {
				temp = it.next();
				if (sortedSet.comparator().compare(v, temp) == 0) {
					it.remove();
					break;
				}
			}
		}
	}
	
}
