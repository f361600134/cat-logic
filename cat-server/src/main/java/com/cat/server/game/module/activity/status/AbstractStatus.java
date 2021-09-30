package com.cat.server.game.module.activity.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.IActivityDomain;

public abstract class AbstractStatus implements IActivityStatus{
	
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	/**
	 * 活动对象
	 */
	protected IActivityDomain activityDomain;
	
	public AbstractStatus(IActivityDomain activityDomain) {
		this.activityDomain = activityDomain;
	}
	
	public Activity getActivity() {
		return activityDomain.getActivity();
	}
	
}
