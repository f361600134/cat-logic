package com.cat.server.game.module.activityoperation.learncommunity;

import com.cat.server.game.module.mission.domain.QuestTypeData;

/**
 * LearnCommunity接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface ILearnCommunityService{
	
	public QuestTypeData getQuestTypeData(long playerId);

}