package com.cat.zproto.domain.proto;

import com.alibaba.fastjson.annotation.JSONField;
import com.cat.zproto.util.StringUtil;

/**
 * 协议字段
 */
public class ProtocolField {
	/**
	 * 标识符，repeated
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
	/**
	 * 下标
	 */
	private int index;
	
	public ProtocolField() {
		this.identifier = "";
		this.type = "";
		this.name = "";
		this.comment = "";
		this.javaType = "";
	}
	
	public String getIdentifier() {
		if (repeated) {
			return "repeated";
		}
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
		this.javaType = StringUtil.protoTypeToJavaType(type);
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
	@JSONField(serialize = false)
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	@JSONField(serialize = false)
	public boolean getPrimitive() {
		return StringUtil.isJavaType(javaType);
	}
	
	public boolean isRepeated() {
		return repeated;
	}
	public void setRepeated(boolean repeated) {
		this.repeated = repeated;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "\n\tProtocolField [identifier=" + identifier + ", type=" + type + ", name=" + name
				+ ", comment=" + comment + "]";
	}
}
