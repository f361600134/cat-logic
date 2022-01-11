package com.cat.server.game.module.activity.type;

import java.util.Map;

/**
 * 活动数据结构
 * @author Jeremy
 *
 */
public interface IActivityData<T extends IActivityPlayerData> {
	
	/**
	 * 获得或创建玩家活动数据
	 * @param playerId 玩家id
	 * @param createIfAbsent 不存在是是否创建
	 * @return
	 */
	T getOrCreate(long playerId, boolean createIfAbsent);
	
	/**
	 * 获取所有玩家活动数据
	 * @return
	 */
	Map<Long, T> getDataMap();
	
	/**
	 * 清掉数据
	 */
	void clear();

}
