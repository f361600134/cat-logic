package com.cat.generator.core.proto.domain;

import com.cat.generator.util.StringUtils;

/**
 * 协议字段
 */
public class ProtocolField {
	/**
	 * 标识符，repeated、optional
	 */
	private String identifier;
	/**
	 *proto 数据类型
	 */
	private String type;
	/**
	 * 字段名称
	 */
	private String name;
	/**
	 * 备注信息
	 */
	private String comment;
	/**
	 * proto对应的Java类型
	 */
	private String javaType;
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		this.javaType = StringUtils.protoTypeToJavaType(type);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	public boolean getPrimitive() {
		return StringUtils.isJavaType(javaType);
	}
	
	@Override
	public String toString() {
		return "\n\tProtocolField [identifier=" + identifier + ", type=" + type + ", name=" + name
				+ ", comment=" + comment + "]";
	}
}
