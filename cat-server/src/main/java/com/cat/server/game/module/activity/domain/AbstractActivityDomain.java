package com.cat.server.game.module.activity.domain;

import static com.cat.server.game.module.activity.status.IActivityStatus.BEGIN;
import static com.cat.server.game.module.activity.status.IActivityStatus.PREPARE;
import static com.cat.server.game.module.activity.status.IActivityStatus.SETTLE;

import com.cat.server.game.module.activity.status.ActivityStatusManager;

/**
 * 活动抽象类
 * @author Jeremy
 */
public abstract class AbstractActivityDomain implements IActivityDomain{
	
	/**
	 * 活动对象数据
	 */
	private Activity activity;
	
	private ActivityStatusManager statusManager = new ActivityStatusManager(this);
	
	public AbstractActivityDomain(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public Activity getActivity() {
		return activity;
	}

//	@Override
//	public ActivityType getType() {
//		return ActivityType.valueOf(getActivity().getId());
//	}
//
//	@Override
//	public int getPlanId() {
//		return activity.getPlanId();
//	}
//
//	@Override
//	public int getStatus() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public boolean isBegin() {
        return activity.getStatus() == BEGIN;
	}

	@Override
	public boolean isSettle() {
		return activity.getStatus() == SETTLE;
	}

	@Override
	public boolean isOpen() {
		int status = activity.getStatus();
		return status == BEGIN || status == SETTLE;
	}

	@Override
	public boolean isInCycle() {
		int status = activity.getStatus();
		return status == PREPARE || status == BEGIN || status == SETTLE;
	}

	@Override
	public void checkAndRefreshStatus(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshConfig() {
		int configId = activity.getConfigId();
		if (configId <= 0) {
			return;
		}
		//TODO 刷新配置,数值覆盖到activity
		
	}

//	@Override
//	public long getBeginTime() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public long getSettleTime() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public long getCloseTime() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	
}
