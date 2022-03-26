package com.cat.server.game.module.mission.goal;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.module.mission.define.GoalTypeEnum;
import com.cat.server.game.module.mission.domain.QuestGoal;

/**
 * 	任务目标类型
 * @auth Jeremy
 */
public interface IQuestGoalType {
	
	/**
	 *	 处理任务目标类型
	 * @return
	 */
	public GoalTypeEnum getMissionGoal();
	
	/**
	 * 刷新任务目标, 当策划配置任务改变, 非玩家行为/事件触发任务完成的任务 
	 * @param playerId 玩家id
	 * @param goal目标对象
	 * @param complateCondition 配置内的完成条件, 一个目标只能是一个条件
	 * @param complateValue 配置内的完成值,一个目标只能是一个值
	 * @return
	 */
	default public boolean refresh(long playerId, QuestGoal goal, int complateCondition, int complateValue) {
		return false;
	}
	
	/**
	 * 监听事件, 处理任务目标逻辑
	 * @param playerId 玩家id
	 * @param event 事件
	 * @param goal目标对象
	 * @param complateCondition 配置内的完成条件, 一个目标只能是一个条件
	 * @param complateValue 配置内的完成值,一个目标只能是一个值
	 * @return
	 */
	public boolean process(long playerId, IEvent event, QuestGoal goal, int complateCondition, int complateValue);
	
	/**
	 * 对应的事件集合
	 * 一个任务有多种触发的事件完成
	 * 比如,玩家登陆任务,对应登陆事件以及玩家每日重置事件.
	 * @return
	 */
	public String[] focusEvents();
	
}
