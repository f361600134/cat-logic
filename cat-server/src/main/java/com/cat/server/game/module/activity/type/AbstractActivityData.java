package com.cat.server.game.module.activity.type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 活动数据抽象类
 * @author Jeremy
 *
 */
public abstract class AbstractActivityData<T extends IActivityPlayerData> implements IActivityData<T>{
	/**
	 * 活动背包
	 * key: 活动id
	 * value: 活动背包
	 */
	private final Map<Long, T> dataMap = new ConcurrentHashMap<>(); 
	
	public AbstractActivityData() {
		
	}

	public abstract T newPlayerData();
	/**
	 * 通过玩家id获取玩家活动数据
	 * @param playerId
	 * @return 血族嘉年华玩家数据
	 */
	@Override
	public T getOrCreate(long playerId, boolean createIfAbsent) {
		T data = dataMap.get(playerId);
		if (data == null && createIfAbsent) {
			data = newPlayerData();
			data.setPlayerId(playerId);
			dataMap.put(playerId, data);
			this.afterCreatePlayerData(data);
		}
		return data;
	}
	
	/**
	 * 创建玩家对象后的操作
	 * @param playerData
	 */
	public void afterCreatePlayerData(T playerData) {
		
	}
	
	@Override
	public Map<Long, T> getDataMap() {
		return dataMap;
	}
	
	@Override
	public void clear() {
		dataMap.clear();
	}
	
}
