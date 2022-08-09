package com.cat.server.game.module.mission;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.game.module.function.IPlayerModuleService;
import com.cat.server.game.module.mission.define.MissionTypeEnum;
import com.cat.server.game.module.mission.domain.QuestTypeData;

/**
 * Mission接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IMissionService extends IPlayerModuleService, ILifecycle{
	
	/**
	 * 	获取任务类型数据
	 * @param playerId 玩家id
	 * @param questTypeId 任务类型id
	 * @return QuestTypeData  任务类型数据
	 * @date 2022年8月7日下午6:08:09
	 */
	QuestTypeData getQuestTypeData(long playerId, MissionTypeEnum missionType);

}