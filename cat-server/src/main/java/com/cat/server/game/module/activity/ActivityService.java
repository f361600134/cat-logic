package com.cat.server.game.module.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.ActivityDomain;
import com.cat.server.game.module.activity.type.IActivityType;


/**
 * Activity控制器, 提供活动相关的查询接口
 * @author Jeremy
 */
@Service
class ActivityService implements IActivityService, ILifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired private ActivityManager activityManager;

	@Override
	public Collection<Activity> getAllActivitys() {
		List<Activity> activitys = new ArrayList<>();
		for (ActivityDomain activityDomain : activityManager.getAllDomain()) {
			activitys.add(activityDomain.getActivity());
		}
		return activitys;
	}

	@Override
	public IActivityType getActivityType(int planId) {
		ActivityDomain domain = activityManager.getDomain(planId);
		if (domain == null) {
			return null;
		}
		return domain;
	}
	
	@Override
	public void start() throws Throwable {
		activityManager.init();
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}
	
}
 
 
