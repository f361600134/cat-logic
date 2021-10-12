package com.cat.server.game.module.activity.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.IActivityType;

public abstract class AbstractStatus implements IActivityStatus{
	
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	/**
	 * 活动对象
	 */
	protected IActivityType activityType;
	
	public AbstractStatus(IActivityType activityType) {
		this.activityType = activityType;
	}
	
	public Activity getActivity() {
		return activityType.getActivity();
	}
	
}
