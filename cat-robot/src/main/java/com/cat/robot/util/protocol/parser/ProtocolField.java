/**
 * 
 */
package com.cat.robot.util.protocol.parser;

/**
 * 协议字段
 */
public class ProtocolField {
	/**
	 * 标识符，repeated、optional
	 */
	private String identifier;
	/**
	 * 数据类型
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
	@Override
	public String toString() {
		return "\n\tProtocolField [identifier=" + identifier + ", type=" + type + ", name=" + name
				+ ", comment=" + comment + "]";
	}
}
