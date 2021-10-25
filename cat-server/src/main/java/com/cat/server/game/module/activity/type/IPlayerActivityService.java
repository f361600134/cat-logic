package com.cat.server.game.module.activity.type;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.activity.IActivityService;

/**
 * 玩家个人活动类service接口, 用于处理个人活动模块的数据, 封装一些公共判断
 * @author Jeremy
 */
public interface IPlayerActivityService {
	
	/**
	 * 活动类型
	 * @return int 活动类型
	 * @date 2021年9月30日上午8:03:46
	 */
	public int activityType();

	/**
	 * 默认方法, 根据活动的类型, 获取活动实现类
	 * @return
	 */
	default public IActivityType getActivityType() {
		IActivityService activityService = SpringContextHolder.getBean(IActivityService.class);
		IActivityType activityType = activityService.getActivityType(activityType());
		return activityType;
	}
	
	
	/**
	 * 是否处于活动中
	 * @return boolean true:是
	 * @date 2021年9月30日上午8:05:07
	 */
	default public ErrorCode isInCycle() {
		IActivityType activityType = getActivityType();
		if (activityType == null) {
			return ErrorCode.ACTIVITY_NOT_EXIST;
		}
		else if (!activityType.isInCycle()) {
			return ErrorCode.ACTIVITY_NOT_IN_ACTIVITY_TIME;
		}
		return ErrorCode.SUCCESS;
	}
	
}
