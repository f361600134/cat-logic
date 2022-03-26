package com.cat.server.game.module.activity;

import java.util.Collection;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.cat.server.game.module.activity.type.IActivityType;

/**
 * Activity接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IActivityService {
	
	/**
	 * 获取服务器下所有活动
	 * @return 活动对象列表
	 */
	public Collection<? extends IActivityType> getAllActivityTypes();
	
	/**
	 * 获取指定类型id的活动域
	 * @return 活动实现类类型
	 */
	public IActivityType getActivityType(int typeId);
	
	/**
	 * 根据指定的活动class,获取到活动
	 * @return 活动实现类类型
	 */
	public <T extends IActivityType> T getActivityType(Class<T> clazz);
	
	/**
	 * 获取指定类型枚举类获得的活动域
	 * @return 活动实现类类型
	 */
	default public <T extends IActivityType> T getActivityType(ActivityTypeEnum activityTypeEnum, Class<T> clazz) {
		IActivityType activityType = getActivityType(activityTypeEnum.getValue());
		if (!clazz.isInstance(activityType)) {
			return null;
		}
		return clazz.cast(activityType);
	}
	
	/**
	 * 获取指定类型id的活动域
	 * @return 活动实现类类型
	 */
	default public <T extends IActivityType> T getActivityType(int typeId, Class<T> clazz) {
		IActivityType activityType = getActivityType(typeId);
		if (!clazz.isInstance(activityType)) {
			return null;
		}
		return clazz.cast(activityType);
	}
	
	/**
	 * 获取有效的/在活动周期内的,活动处理类<br>
	 * 如果为null, 或未处于活动周期内, 则返回错误码, 
	 * @return 错误码为success时, 才返回活动代理类
	 */
	default public <T extends IActivityType> ResultCodeData<T> getUsableActivityType(int typeId, Class<T> clazz) {
		T activityType = getActivityType(typeId, clazz);
		if (activityType == null) {
			return ResultCodeData.of(ErrorCode.ACTIVITY_NOT_EXIST);
		}
		else if (!activityType.isInCycle()) {
			return ResultCodeData.of(ErrorCode.ACTIVITY_NOT_IN_ACTIVITY_TIME);
		}
		return ResultCodeData.of(activityType);
	}
	
	/**
	 * 获取有效的/在活动周期内的,活动处理类<br>
	 * 如果为null, 或未处于活动周期内, 则返回错误码, 不会返回活动数据
	 * @return
	 */
	default public <T extends IActivityType> ResultCodeData<T> getUsableActivityType(ActivityTypeEnum activityTypeEnum, Class<T> clazz) {
		return getUsableActivityType(activityTypeEnum.getValue(), clazz);
	}
	
}