package com.cat.zproto.domain.table.po;

import java.util.List;

import com.google.common.collect.Lists;

public class Entity<V extends Properties>{
	
	/**
	 * 实体名
	 */
	protected String entityName;
	/**
	 * 实体内字段
	 */
	protected List<V> properties;
	
	public Entity() {
		this.properties = Lists.newArrayList();
	}
	public Entity(String entityName) {
		this.entityName = entityName;
		this.properties = Lists.newArrayList();
	}
	public Entity(String entityName, List<V> properties) {
		super();
		this.entityName = entityName;
		this.properties = properties;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public List<V> getProperties() {
		return properties;
	}
	public void setProperties(List<V> properties) {
		this.properties = properties;
	}
	public void addProperties(V property) {
		property.setIndexId(getNextIndexId());
		this.properties.add(property);
	}
	private int getNextIndexId() {
		int size = properties.size();
		if (size == 0) {
			return 1;
		}else{
			V v = properties.get(size-1);
			return v.getIndexId() + 1;
		}
	}
	
	@Override
	public String toString() {
		return "Entity [entityName=" + entityName + ", properties=" + properties + "]";
	}
	
}
