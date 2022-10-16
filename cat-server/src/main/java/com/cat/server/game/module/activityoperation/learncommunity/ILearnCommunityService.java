package com.cat.server.game.module.activityoperation.learncommunity;

import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunity;
import com.cat.server.game.module.function.IPlayerModuleService;
import com.cat.server.game.module.mission.domain.QuestTypeData;

/**
 * LearnCommunity接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface ILearnCommunityService extends IPlayerModuleService{
	
	/**
	 * 获取玩家研习社数据
	 * @param playerId 玩家id
	 * @return
	 */
	public LearnCommunity getLearnCommunity(long playerId);
	
	/**
	 * 获取玩家任务数据
	 * @param playerId 玩家id
	 * @return
	 */
	public QuestTypeData getQuestTypeData(long playerId);

}