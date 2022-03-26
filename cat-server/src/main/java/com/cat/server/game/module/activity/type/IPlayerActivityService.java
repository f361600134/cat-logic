package com.cat.server.game.module.activity.type;

import java.util.Collection;
import java.util.Collections;

import com.cat.server.game.helper.result.ErrorCode;

/**
 * 玩家个人活动类service接口, 用于处理个人活动模块的数据, 封装一些公共判断
 * @author Jeremy
 * @deprecated
 */
public interface IPlayerActivityService<T extends IActivityType, D extends IActivityPlayerData> {
	
	/**
	 * 活动类型
	 * @return int 活动类型
	 * @date 2021年9月30日上午8:03:46
	 */
	public ActivityTypeEnum activityType();
	
//	/**
//	 * 活动类型
//	 * @return int 活动类型
//	 * @date 2021年9月30日上午8:03:46
//	 */
//	public Class<? extends IActivityType> activityClazz();

	/**
	 * 默认方法, 根据活动的类型, 获取活动实现类
	 * @return
	 */
	public T getActivityType();
	
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
	
	default public Collection<D> geAllPlayerData() {
		return Collections.emptyList();
	}
	
}
