package com.cat.server.game.module.rank.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * 根据比较器排序的Map, 线程不安全, 暂时没想到好办法线程安全化
 * 外部调用如果要保证线程安全, 就得想办法串行化调用.
 * @author Jeremy
 * @param <K>
 * @param <V>
 */
public class SortedValueMap<K,V> implements ISortedMap<K,V>{
	
	/**
	 * 	双向map, key, value必须唯一
	 */
	private BiMap<K,V> biMap;
	
	/**
	 * 	有序的set集
	 */
    private TreeSet<V> sortedSet; 
    
    public SortedValueMap(int maximum, Comparator<? super V> comparator){
    	if (maximum <= 0) {
    		throw new IllegalArgumentException("the input parameter {maximum} is not allowed less than zero");
		}
		this.biMap = HashBiMap.create();
    	this.sortedSet = new TreeSet<V>(comparator);
    }
    
    /***
     * 注意comparator int compared(Object obj1,Object obj2)不能返回 0,
     * 否则sorted认为是同一对象只保存一个. 
     * @param comparator
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

	@Override
	public V put(K key, V value) {
		remove(key);
		sortedSet.add(value);
		return biMap.put(key, value);
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
		return biMap.values();
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
	public SortedSet<V> headSet(V v){
		return sortedSet.headSet(v);
	}
	
	@Override
	public SortedSet<V> tailSet(V v){
		return sortedSet.tailSet(v);
	}
	
	@Override
	public SortedSet<V> subSet(V fv, V tv){
		return sortedSet.subSet(fv, tv);
	}
	

	@Override
	public Iterator<V> iterator() {
		return sortedSet.iterator();
	}
	

	@Override
	public Object[] toArray() {
		return sortedSet.toArray();
	}
	
	/**
	 * 移除map, set中的数值
	 * @param v
	 * @return  
	 * @return V  
	 * @date 2019年7月5日下午4:04:24
	 */
	public void removeValue(V v) {
		Object obj = biMap.inverse().remove(v);
//		if(obj != null){
//			this.removeFromSortedSet(v);
//		}
	}

	@Override
	public K getKey(V v) {
		return biMap.inverse().get(v);
	}
	
	@Override
	public String toString() {
		return "biMap:"+biMap.size()+", sortedSet:"+sortedSet.size();
	}
	
	
	private void removeFromSortedSet(V v) {
		/*
		 * 用sortedSet.remove会出现明明对象存在但删不了的情况
		 * 所以改用该方法.
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
