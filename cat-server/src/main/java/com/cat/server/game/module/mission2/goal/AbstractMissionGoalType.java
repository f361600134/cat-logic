package com.cat.server.game.module.mission2.goal;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.module.mission2.type.MissionGoal;

/**
 * 抽象任务处理类
 * @auth Jeremy
 * @date 2022年3月20日下午7:46:35
 */
public abstract class AbstractMissionGoalType implements IMissionGoalType {

	/**
	 * 根据任务目标类型, 获取
	 */
	@Override
	public boolean process(long playerId, IEvent event, MissionGoal goal) {
		
		
		return false;
	}
	
}
