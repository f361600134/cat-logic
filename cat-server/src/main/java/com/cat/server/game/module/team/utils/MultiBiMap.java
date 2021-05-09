package com.cat.server.game.module.team.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * 线程不安全, 维护一个双向map, 以及multiMap
 * 可以通过K查询到V, 亦可以通过V查询到K
 * @author Jeremy
 *
 * @param <K>
 * @param <V>
 */
public class MultiBiMap<K,V> {
	
	/**
	 * Key: 队伍id
	 * value: 玩家id列表
	 */
	private Multimap<V, K> multiMap = ArrayListMultimap.create();
	
	/**
	 * key: 玩家id
	 * value:队伍id
	 */
	private Map<K, V> hashMap = new HashMap<>();
	
	public MultiBiMap() {}

//	public int size() {
//		return hashMap.size();
//	}
//
//	public boolean isEmpty() {
//		return hashMap.isEmpty();
//	}

//	@Override
//	public boolean containsKey(Object key) {
//		return false;
//	}
//
//	@Override
//	public boolean containsValue(Object value) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	public V getByKey(Object key) {
		return hashMap.get(key);
	}
	
	public Collection<K> getByValue(V value) {
		return multiMap.get(value);
	}

	public V put(K key, V value) {
		multiMap.put(value, key);
		return hashMap.put(key, value);
	}

	public V remove(Object key) {
		V value = hashMap.remove(key);
		multiMap.remove(value, key);
		return value;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			V value = m.get(key);
			multiMap.put(value, key);
			hashMap.put(key, value);
		}
	}

	public void clear() {
		multiMap.clear();
		hashMap.clear();
	}

//	@Override
//	public Set<K> keySet() {
//		return null;
//	}
//
//	@Override
//	public Collection<V> values() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Set<Entry<K, V>> entrySet() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public static void main(String[] args) {
		MultiBiMap<Integer, Integer> map = new MultiBiMap<>();
		//队伍1
		map.put(1, 1001);
		map.put(2, 1001);
		map.put(3, 1001);
		//队伍2
		map.put(4, 1002);
		map.put(5, 1002);
		
		System.out.println(map);
		System.out.println("通过玩家id查询队伍id:"+map.getByKey(4));
		System.out.println("通过队伍id查询玩家ids:"+map.getByValue(1002));
		
	}

	@Override
	public String toString() {
		return "MultiBiMap [multiMap=" + multiMap + ", hashMap=" + hashMap + "]";
	}
	

}
