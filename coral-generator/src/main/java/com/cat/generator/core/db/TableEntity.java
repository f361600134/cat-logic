package com.cat.generator.core.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * 表对象信息, 解析后的表信息存储于此对象
 * 
 * @auth Jeremy
 * @date 2019年9月16日上午10:56:00
 */
public class TableEntity {

	private String tablName; // 表名
	private String entityName;// 实体名
	private List<TableBean> entityBeans;// 实体内属性
	/**
	 * 索引map, 一般不建议设置主键后,又创建索引, 但是为了避免出问题, 对索引去重,剔除掉已存在的主键索引
	 */
	private Map<String, List<String>> indexMap;
	private List<String> primaryKeys;// 主键

	public TableEntity() {
		entityBeans = new ArrayList<>();
		indexMap = new HashMap<>();
	}

	public TableEntity(String entityName) {
		super();
		this.entityName = entityName;
		entityBeans = new ArrayList<>();
		indexMap = new HashMap<>();
	}

	public TableEntity(String entityName, List<TableBean> entityBeans) {
		super();
		this.entityName = entityName;
		this.entityBeans = entityBeans;
	}

	public String getTablName() {
		return tablName;
	}

	public void setTablName(String tablName) {
		this.tablName = tablName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public List<TableBean> getEntityBeans() {
		return entityBeans;
	}

	public void setEntityBeans(List<TableBean> entityBeans) {
		this.entityBeans = entityBeans;
	}

	public void addEntityBeans(TableBean entityBean) {
		this.entityBeans.add(entityBean);
	}

	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	public boolean keysEmpty() {
		return primaryKeys.isEmpty();
	}

	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public Map<String, List<String>> getIndexMap() {
		return indexMap;
	}

	public List<String> getIndexs() {
		List<String> ret = new ArrayList<>();
		indexMap.forEach((key, values) -> {
			ret.addAll(values);
		});
		return ret;
	}

	public void setIndexMap(Map<String, List<String>> indexMap) {
		this.indexMap = indexMap;
	}

	@Override
	public String toString() {
		return "ExcelEntity [entityName=" + entityName + ", entityBeans=" + entityBeans + ", indexMap=" + indexMap
				+ "]";
	}

	/**
	 * 获取主键
	 * 
	 * @return
	 */
	public String genPrimary() {
		StringBuilder sb = new StringBuilder();
		for (String primary : primaryKeys) {
			sb.append("`").append(primary).append("`").append(",");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 获取主键1
	 * 
	 * @return
	 */
	public String genPrimary1() {
		if (primaryKeys != null && !primaryKeys.isEmpty()) {
			return primaryKeys.get(0);
		}
		return null;
	}

	/**
	 * 添加索引
	 * 
	 * @param indexName
	 * @param indexVal
	 */
	public void addIndex(String indexName, String indexVal) {
		List<String> list = indexMap.get(indexName);
		if (list == null) {
			list = new ArrayList<>();
			indexMap.put(indexName, list);
		}
//		if (primaryKeys.contains(indexVal)) {
//			return;
//		}
		list.add(indexVal);
	}

	/**
	 * 设置主键
	 */
	public void setPrimarys(List<String> primarys) {
		int index = 0;
		for (String primary : primarys) {
			for (TableBean excelBean : entityBeans) {
				if (excelBean.getField().equals(primary)) {
					++index;
					excelBean.setPrimary(String.valueOf(index));
				}
			}
		}
		this.primaryKeys = primarys;
	}

	/**
	 * 生成toSting方法
	 * 
	 * @return
	 * @return String
	 * @date 2019年9月16日上午10:25:13
	 */
	public String genToStr() {
		StringBuilder builder = new StringBuilder();
		builder.append("\"").append(entityName);
		int length = 0;
		for (TableBean excelBean : entityBeans) {
			length++;
			if (length == 1) {
				builder.append(" [");
			} else {
				builder.append(", ");
			}
			builder.append(excelBean.getField()).append("= \"+ ").append(excelBean.getField());
			if (length % 5 == 0) {
				builder.append("\n\t\t\t\t");
			}
			if (length == entityBeans.size()) {
				builder.append("+\"]\";");
			} else {
				builder.append(" +\"");
			}
		}
		return builder.toString();
	}

	/**
	 * 生成索引名称
	 * 
	 * @return
	 * @return String
	 * @date 2019年9月17日下午1:05:27
	 */
	public String genIndexNames() {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<String, List<String>>> iter = indexMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, List<String>> entry = iter.next();
			entry.getValue().forEach((val) -> {
				builder.append(val).append(":");
			});
		}
		if (builder.length() > 0)
			builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	/**
	 * 获取主键和索引, 索引要去重 keys+indexs
	 * 
	 * @return
	 */
	public List<String> getKeyAndIndexs() {
		List<String> keyAndIndexs = new ArrayList<>(primaryKeys);
		Iterator<Entry<String, List<String>>> iter = indexMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, List<String>> entry = iter.next();
			for (String val : entry.getValue()) {
				if (primaryKeys.contains(val)) {
					continue;
				}
				keyAndIndexs.add(val);
			}
//			entry.getValue().forEach((val) -> {
//				keyAndIndexs.add(val);
//			});
		}
		return keyAndIndexs;
	}

	/**
	 * 获取主键和索引, 索引要去重 keys+indexs
	 * 
	 * @return
	 */
	public List<String> getIndexsWithoutKey() {
		List<String> indexs = new ArrayList<>();
		Iterator<Entry<String, List<String>>> iter = indexMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, List<String>> entry = iter.next();
			entry.getValue().forEach((val) -> {
				indexs.add(val);
			});
		}
		return indexs;
	}

//	/**
//	 * 获取所有索引列
//	 * @return
//	 */
//	public List<String> getKeyAndIndexs(){
//		
//	}

//	/**
//	 * 生成数据库字段
//	 * @return
//	 */
//	public String getFileds() {
//		StringBuilder builder = new StringBuilder();
//		for (TableBean excelBean : entityBeans) {
//			builder.append(excelBean.getTbField()).append(",");
//		}
//		builder.deleteCharAt(builder.length() - 1);
//		return builder.toString();
//	}

//	/**
//	 * java字段
//	 * @return
//	 */
//	public String getJavaFiled() {
//		StringBuilder builder = new StringBuilder();
//		for (TableBean excelBean : entityBeans) {
//			builder.append(":ei.").append(excelBean.getField()).append(",");
//		}
//		builder.deleteCharAt(builder.length() - 1);
//		return builder.toString();
//	}

//	/**
//	 * 生成主键
//	 */
//	public String getPrimaryKey() {
//		Map<Integer, TableBean> primaryMap = null;
//		try {
//			primaryMap = new TreeMap<Integer, TableBean>();
//			for (TableBean excelBean : entityBeans) {
//				if (excelBean.getPrimary() == null) 
//					continue;
//				int primary = Integer.parseInt(excelBean.getPrimary());
//				if (primary > 0) {
//					primaryMap.put(primary, excelBean);
//				}
//			}
//			if (primaryMap.isEmpty())
//				throw new RuntimeException("请设置主键!, entityName:"+entityName) ;
//			
//			StringBuilder builder = new StringBuilder();
//			String plus = "+\"-\"+";
//			for (TableBean excelBean : primaryMap.values()) {
//				builder.append(excelBean.getField());
//				builder.append(plus);
//			}
//			int start = builder.length() - plus.length();
//			builder.delete(start, builder.length());
//			return builder.toString();
//		} catch (Exception e) {
//			throw new RuntimeException("生成主键异常, entityName:"+entityName+", primaryMap:"+primaryMap, e) ;
//		}
//	}

}
