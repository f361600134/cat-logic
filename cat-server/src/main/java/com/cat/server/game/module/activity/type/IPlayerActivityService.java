package com.cat.server.game.module.activity.type;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.activity.IActivityService;

public interface IPlayerActivityService {
	
	/**
	 * 活动类型
	 * @return  
	 * @return int  
	 * @date 2021年9月30日上午8:03:46
	 */
	public int activityType();
	
	default public IActivityType getActivityType() {
		IActivityService activityService = SpringContextHolder.getBean(IActivityService.class);
		IActivityType activityType = activityService.getActivityType(activityType());
		return activityType;
	}
	
	
	/**
	 * 是否处于活动中
	 * @return  
	 * @return boolean  
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
	
//	/**
//	 * 是否处于活动中
//	 * @return  
//	 * @return boolean  
//	 * @date 2021年9月30日上午8:05:07
//	 */
//	default public ErrorCode isOpen() {
//		IActivityService activityService = SpringContextHolder.getBean(IActivityService.class);
//		IActivityType activityType = activityService.getActivityType(activityType());
//		if (activityType == null) {
//			return ErrorCode.ACTIVITY_NOT_EXIST;
//		}
//		else if (!activityType.isOpen()) {
//			return ErrorCode.ACTIVITY_NOT_IN_ACTIVITY_TIME;
//		}
//		return ErrorCode.SUCCESS;
//	}

	

}
