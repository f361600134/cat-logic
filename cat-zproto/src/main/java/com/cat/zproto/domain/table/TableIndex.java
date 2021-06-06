package com.cat.zproto.domain.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 	表索引信息
 * @auth Jeremy
 * @date 2019年9月17日上午9:35:06
 */
public class TableIndex {
	
	//k:索引名称 v:索引组
	private Map<String, List<String>> indexMap;

	public Map<String, List<String>> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(Map<String, List<String>> indexMap) {
		this.indexMap = indexMap;
	}
	
	public void addIndex(String indexName, String indexVal) {
		List<String> list = indexMap.get(indexName);
		if (list == null) {
			list = new ArrayList<>();
			indexMap.put(indexName, list);
		}
		list.add(indexVal);
	}
	
}
