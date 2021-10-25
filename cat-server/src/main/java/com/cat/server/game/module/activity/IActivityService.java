package com.cat.server.game.module.activity;

import java.util.Collection;

import com.cat.server.game.module.activity.domain.Activity;
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
	public Collection<Activity> getAllActivitys();
	
	/**
	 * 获取指定类型id的活动域
	 * @return 活动实现类类型
	 */
	public IActivityType getActivityType(int typeId);
	
	/**
	 * 获取指定类型枚举类获得的活动域
	 * @return 活动实现类类型
	 */
	public IActivityType getActivityType(ActivityTypeEnum activityType);
	
}