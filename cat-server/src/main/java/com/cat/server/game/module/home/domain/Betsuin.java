package com.cat.server.game.module.home.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 别院数据对象
 * @author Jeremy
 */
public class Betsuin {
	
	/**
	 * 别院配置id, 对应地图id<br>
	 */
	private int id;
	
	/**
	 * 家具位置Map<br>
	 * 应用方案后, 把方案内的的家具, 覆盖到此map<br>
	 * 需要对所有的家具进行数量判断, 数量不足, 则不加入到此map
	 * key: 家具唯一id
	 * value： 家具方位
	 */
	private Map<Long, FurnitureLocation> locationMap = new HashMap<>();
	
	public Betsuin() {}
	
	public Betsuin(int id, Map<Long, FurnitureLocation> locationMap) {
		this.id = id;
		this.locationMap = locationMap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<Long, FurnitureLocation> getLocationMap() {
		return locationMap;
	}

	public void setLocationMap(Map<Long, FurnitureLocation> locationMap) {
		this.locationMap = locationMap;
	}
	
	public static Betsuin create(int configId) {
		Betsuin betsuin = new Betsuin();
		betsuin.setId(configId);
		return betsuin;
	}
	
	
}
