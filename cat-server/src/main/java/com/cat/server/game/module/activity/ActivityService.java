package com.cat.server.game.module.activity;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.module.activity.type.IActivityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


/**
 * Activity控制器, 提供活动相关的查询接口
 * @author Jeremy
 */
@Service
class ActivityService implements IActivityService, ILifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired 
	private ActivityManager activityManager;
	
	@Override
	public Collection<? extends IActivityType> getAllActivityTypes() {
		return activityManager.getAllDomain();
	}

	@Override
	public IActivityType getActivityType(int typeId) {
		return activityManager.getOrLoadDomain(typeId);
	}
	
	@Override
	public <T extends IActivityType> T getActivityType(Class<T> clazz) {
		for (IActivityType activityType : activityManager.getAllDomain()) {
			if (clazz.isInstance(activityType)) {
				return clazz.cast(activityType);
			}
		}
		return null;
	}
	
	@Override
	public void start() throws Throwable {
		activityManager.init();
	}
	
	@Override
	public void stop() throws Exception{
		activityManager.destory();
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}

}
 
 
