package com.cat.server.game.module.activity.type;

/**
 * 玩家活动排行接口
 * @author Jeremy
 */
public interface IActivityPlayerRankData extends IActivityPlayerData{
	
	public boolean isRankReward();

	public void setRankReward(boolean bool);
	
}
