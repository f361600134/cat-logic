package com.cat.server.game.module.activity.type;

/**
 * 玩家活动数据结构
 * @author Jeremy
 */
public interface IActivityPlayerData {
	
	/**
	 * 设置玩家id
	 * @param 玩家id
	 */
	public void setPlayerId(long playerId);
	
	/**
	 * @return 玩家id
	 */
	public long getPlayerId();
	
}
