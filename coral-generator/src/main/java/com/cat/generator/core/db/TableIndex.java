package com.cat.generator.core.db;

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
	
//	private String indexName;//索引名称
//	private String fieldArr; //字段组, 需要跟字段相同
//	private String options;	 //所支持的操作选项
//	private List<String> indexList;
	
//	public String getIndexName() {
//		return indexName;
//	}
//	public void setIndexName(String indexName) {
//		this.indexName = indexName;
//	}
	
//	public void setFieldArr(String fieldArr) {
//		this.fieldArr = fieldArr;
//	}
//	public void setOptions(String options) {
//		this.options = options;
//	}
	
//	public List<String> getFieldList() {
//		fieldList = Arrays.asList(fieldArr.split(","));
//		return fieldList;
//	}
//	public List<String> getOptionList() {
//		optionList = Arrays.asList(options.split(","));
//		return optionList;
//	}
	
//	public List<String> getIndexList() {
//		return indexList;
//	}
//	public void setIndexList(List<String> indexList) {
//		this.indexList = indexList;
//	}
	
}
