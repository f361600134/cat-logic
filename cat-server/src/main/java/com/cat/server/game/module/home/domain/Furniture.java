package com.cat.server.game.module.home.domain;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.home.proto.PBHomeFurnitureBuilder;

/**
 * 布局家具对象
 * @author 家具对象
 */
public class Furniture {
	
	/**
	 * 家具唯一id
	 */
	private long uniqueId;
	
	/**
	 * 家居配置id
	 */
	private int configId;

	public long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}
	
	/**
	 * 家具对象转家具协议对象
	 * @return
	 */
	public PBHomeFurnitureBuilder toProto() {
		PBHomeFurnitureBuilder builder = PBHomeFurnitureBuilder.newInstance();
		builder.setUniqueId(uniqueId);
		builder.setConfigId(configId);
		return builder;
	}
	
	/**
	 * 创建一个布局家具对象
	 * @param configId
	 * @return
	 */
	public static Furniture create(int configId) {
		Furniture furniture = new Furniture();
		furniture.setConfigId(configId);
		furniture.setUniqueId(SpringContextHolder.getBean(SnowflakeGenerator.class).nextId());
		return furniture;
	}
	
}
