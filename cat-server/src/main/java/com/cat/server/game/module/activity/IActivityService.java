package com.cat.server.game.module.activity;

import java.util.Collection;

import com.cat.server.game.module.activity.domain.Activity;

/**
 * Activity接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IActivityService {
	
	/**
	 * 获取服务器下所有活动
	 * @return
	 */
	public Collection<Activity> getAllActivitys(int serverId);
	
	/**
	 * 获取指定类型id的活动
	 * @return
	 */
	public Activity getActivity(int serverId, int activityType);
	
	
	
	
	

}