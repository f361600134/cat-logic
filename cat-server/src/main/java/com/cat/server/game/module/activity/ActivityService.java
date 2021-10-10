package com.cat.server.game.module.activity;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.ActivityDomain;


/**
 * Activity控制器, 提供活动相关的查询接口
 * @author Jeremy
 */
@Service
class ActivityService implements IActivityService {
	
	private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired private ActivityManager activityManager;

	@Override
	public Collection<Activity> getAllActivitys(int serverId) {
		// TODO Auto-generated method stub
		ActivityDomain domain = activityManager.getDomain(serverId);
		return null;
	}

	@Override
	public Activity getActivity(int serverId, int activityType) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
 
 
