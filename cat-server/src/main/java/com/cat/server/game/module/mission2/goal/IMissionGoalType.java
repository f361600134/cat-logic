package com.cat.server.game.module.mission2.goal;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.module.mission.domain.MissionGoalEnum;
import com.cat.server.game.module.mission2.type.MissionGoal;

/**
 * 	任务目标类型
 * @auth Jeremy
 */
public interface IMissionGoalType {
	
	/**
	 *	 处理任务目标类型
	 * @return
	 */
	public MissionGoalEnum getMissionGoal();
	
	/**
	 *	处理任务目标逻辑
	 * @param playerId
	 * @param event 事件
	 * @param goal目标对象
	 * @param complateCondition 配置内的完成条件, 一个目标只能是一个条件
	 * @param complateValue 配置内的完成值,一个目标只能是一个值
	 * @return
	 */
	public boolean process(long playerId, IEvent event, MissionGoal goal, int complateCondition, int complateValue);
	
	/**
	 * 对应的事件集合
	 * 一个任务有多种触发的事件完成
	 * 比如,玩家登陆任务,对应登陆事件以及玩家每日重置事件.
	 * @return
	 */
	public String[] focusEvents();
	
}
