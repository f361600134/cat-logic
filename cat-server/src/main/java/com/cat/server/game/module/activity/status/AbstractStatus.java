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
	
	//当前状态的下一个状态
	protected IActivityStatus nextStatus;
	
	public AbstractStatus(IActivityType activityType) {
		this.activityType = activityType;
	}
	
	public Activity getActivity() {
		return activityType.getActivity();
	}

	public IActivityStatus getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(IActivityStatus nextStatus) {
		this.nextStatus = nextStatus;
	}
	
	@Override
	public void handle(long now) {
		Activity activity = getActivity();
        activity.setStatus(getCode());
        activity.save();
	}
	
}
