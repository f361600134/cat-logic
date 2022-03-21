package com.cat.server.game.module.resource.domain;

import com.alibaba.fastjson.JSON;

/**
 * 游戏内的资源对象
 * @author Jeremy
 * @NotUse
 */
public class Resource {
	
	/**
	 * 资源配置id
	 */
	private final int configId;
	/**
	 * 资源类型
	 */
	private final int type;
	/**
	 * 数量
	 */
	private final int number;
	
	public Resource(int configId, int type, int number) {
		super();
		this.configId = configId;
		this.type = type;
		this.number = number;
	}
	
	public int getConfigId() {
		return configId;
	}
	
//	void setConfigId(int configId) {
//		this.configId = configId;
//	}
	
	public int getType() {
		return type;
	}
//	void setType(int type) {
//		this.type = type;
//	}
	
	public int getNumber() {
		return number;
	}
//	void setNumber(int number) {
//		this.number = number;
//	}
	
	
	public static void main(String[] args) {
		Resource resource = new Resource(1, 1, 1);
		String json = JSON.toJSONString(resource);
		System.out.println("序列化前:"+json);
		
		Resource res2 = JSON.parseObject(json, Resource.class);
		System.out.println("反序列化后:"+res2);
		
	}

	@Override
	public String toString() {
		return "Resource [configId=" + configId + ", type=" + type + ", number=" + number + "]";
	}
	
	
}
