package com.cat.zproto.domain.table.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 表对象信息, 解析后的表信息存储于此对象
 * 
 * @auth Jeremy
 * @date 2019年9月16日上午10:56:00
 */
public class TableEntity extends Entity<Properties>{

	/**
	 * 数据库表名
	 */
	private String tablName;
	/**
	 * 依赖对象<br>
	 */
	private Map<String, Entity<AssistProperties>> assistEntityMap; //辅助字段
	/**
	 * 索引map, 一般不建议设置主键后,又创建索引, 但是为了避免出问题, 对索引去重,剔除掉已存在的主键索引
	 */
	private transient Map<String, List<String>> indexMap;
	/**
	 * 主键列表
	 */
	private transient List<String> primaryKeys;
	
	public static void main(String[] args) {
		TableEntity entity = new TableEntity();
		entity.setTablName("Player");
		entity.setEntityName("Player");
		
		//设置bean的成员变量
		Properties properties = new Properties();
		properties.setType("long");
		properties.setField("id");
		properties.setDesc("玩家id");
		properties.setInit("0L");
		properties.setKeyword("transient");
		entity.addProperties(properties);
		
		properties = new Properties();
		properties.setType("int");
		properties.setField("brothelLevel");
		properties.setDesc("红人馆等级");
		properties.setInit("0");
		properties.setKeyword("final");
		entity.addProperties(properties);
		
		properties = new Properties();
//		properties.setType("ConcurrentMap<Integer, SlaveData>");
		properties.setField("slaveDatas");
		properties.setDesc("红人馆,奴隶数据");
//		properties.setInit("new ConcurrentHashMap<>()");
		properties.setKeyword("final");
		entity.addProperties(properties);
		
		//设置bean的辅助对象信息
		Entity<AssistProperties> assist = new Entity<AssistProperties>();
		assist.setEntityName("SlaveData");
		
		AssistProperties assistProperties = new AssistProperties();
		assistProperties.setDesc("奴隶唯一id");
		assistProperties.setInit("0L");
		assistProperties.setField("id");
		assistProperties.setType("int");
		assistProperties.setEntityName("SlaveData");
		assist.addProperties(assistProperties);
		
		assistProperties = new AssistProperties();
		assistProperties.setDesc("外面特征");
//		assistProperties.setInit("new ArrayList<Integer>(5)");
		assistProperties.setField("features");
//		assistProperties.setType("List<Integer>");
		assistProperties.setKeyword("final");
		assistProperties.setEntityName("SlaveData");
		assist.addProperties(assistProperties);
		
		entity.getAssistEntityMap().put(assistProperties.getEntityName(), assist);
		
		String json = JSON.toJSONString(entity, true);
		System.out.println(json);
	}

	public TableEntity() {
		this.indexMap = new HashMap<>();
		this.primaryKeys = new ArrayList<>();
		this.assistEntityMap = new HashMap<>(); 
	}

	public TableEntity(String entityName) {
		super();
		this.entityName = entityName;
		this.indexMap = new HashMap<>();
		this.primaryKeys = new ArrayList<>();
		this.assistEntityMap = new HashMap<>(); 
	}

	public TableEntity(String entityName, List<Properties> entityBeans) {
		super();
		this.entityName = entityName;
		this.primaryKeys = new ArrayList<>();
		this.assistEntityMap = new HashMap<>(); 
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

	@JSONField(serialize = false)
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
		return "ExcelEntity [entityName=" + entityName  + ", indexMap=" + indexMap+ "]";
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
		list.add(indexVal);
	}

	/**
	 * 设置主键
	 */
	public void setPrimarys(List<String> primarys) {
		this.primaryKeys = primarys;
	}

	/**
	 * 生成toSting方法
	 * 
	 * @return
	 * @return String
	 * @date 2019年9月16日上午10:25:13
	 */
	@JSONField(serialize = false)
	public String genToStr() {
		StringBuilder builder = new StringBuilder();
		builder.append("\"").append(entityName);
		int length = 0;
		for (Properties excelBean : getProperties()) {
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
			if (length == getProperties().size()) {
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
	@JSONField(serialize = false)
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
	@JSONField(serialize = false)
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
		}
		return keyAndIndexs;
	}

	/**
	 * 获取主键和索引, 索引要去重 keys+indexs
	 * 
	 * @return
	 */
	@JSONField(serialize = false)
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

	public Map<String, Entity<AssistProperties>> getAssistEntityMap() {
		return this.assistEntityMap;
	}

	public void setAssistEntityMap(Map<String, Entity<AssistProperties>> assistEntityMap) {
		this.assistEntityMap = assistEntityMap;
	}

	/**
	 * 获取或创建辅助对象
	 * @param assistEntityName 辅助对象名
	 * @return
	 */
	public Entity<AssistProperties> getOrCreateAssistEntity(String assistEntityName){
		Entity<AssistProperties> assist = this.assistEntityMap.get(assistEntityName);
		if (assist == null) {
			assist = new Entity<AssistProperties>(assistEntityName);
			this.assistEntityMap.put(assist.getEntityName(), assist);
		}
		return assist;
	}

}
