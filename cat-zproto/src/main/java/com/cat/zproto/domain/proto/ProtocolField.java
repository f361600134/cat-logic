package com.cat.zproto.domain.proto;

import com.cat.zproto.util.StringUtils;

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
     * 该参数是否为list
     */
    private boolean repeated;
	/**
	 * proto对应的Java类型
	 */
	private String javaType;
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
		if (identifier.equals("repeated")) {
			this.repeated = true;
		}
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
	
	public boolean isRepeated() {
		return repeated;
	}
	public void setRepeated(boolean repeated) {
		this.repeated = repeated;
	}
	@Override
	public String toString() {
		return "\n\tProtocolField [identifier=" + identifier + ", type=" + type + ", name=" + name
				+ ", comment=" + comment + "]";
	}
}
