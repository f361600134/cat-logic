package com.cat.server.game.data.config.local.ext.struct;

import java.util.Collections;
import java.util.Map;

public class MultiMap {
	
	/**
	 * 嵌套Map
	 */
	private Map<Integer, Map<Integer, Integer>> map;
	
	/**
	 * 通过key获取到值
	 * @param key
	 * @return
	 */
	public Map<Integer, Integer> get(int key){
		return map.getOrDefault(key, Collections.emptyMap());
	}
	
//	public static void main(String[] args) { 
//		Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
//		map.put(2, new HashMap<>());
//		Map<Integer, Map<Integer, Integer>> newMap = Collections.unmodifiableMap(map);
////		newMap.put(1, new HashMap<>());
//		Map<Integer, Integer> ret = newMap.get(2);
//		System.out.println(ret);
//		ret.put(1, 2);
//		System.out.println(ret);
//	}
	
}
