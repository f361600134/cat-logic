package com.cat.server.game.module.activity.event;

import com.cat.server.core.event.BaseEvent;
import com.cat.server.core.event.PlayerBaseEvent;

/**
 * 通知玩家, 活动状态改变事件
 * @author Jeremy
 */
public class ActivityStatusUpdateEvent extends PlayerBaseEvent {
	
	/**
	 * 活动配置id
	 */
	private final int activityId;
	/**
	 * 活动类型id
	 */
	private final int activityTypeId;
	/**
	 * 活动状态
	 */
	private final int status;
	
	/**
	 * @param activityId 活动id
	 * @param activityTypeId 活动类型id
	 * @param status 状态
	 */
	public ActivityStatusUpdateEvent(int activityId, int activityTypeId, int status) {
		this.activityId = activityId;
		this.activityTypeId = activityTypeId;
		this.status = status;
	}
	
	public int getActivityId() {
		return activityId;
	}

	public int getActivityTypeId() {
		return activityTypeId;
	}

	public int getStatus() {
		return status;
	}
	
	/**
	 * @param activityId 活动id
	 * @param activityTypeId 活动类型id
	 * @param status 状态
	 */
	public static ActivityStatusUpdateEvent create(int activityId, int activityTypeId, int status) {
		return new ActivityStatusUpdateEvent(activityId, activityTypeId, status);
	}
	
}
