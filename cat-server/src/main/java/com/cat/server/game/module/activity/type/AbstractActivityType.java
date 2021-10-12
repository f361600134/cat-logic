package com.cat.server.game.module.activity.type;

import static com.cat.server.game.module.activity.status.IActivityStatus.BEGIN;
import static com.cat.server.game.module.activity.status.IActivityStatus.PREPARE;
import static com.cat.server.game.module.activity.status.IActivityStatus.SETTLE;

import com.cat.server.game.module.activity.domain.Activity;

/**
 * 活动抽象类类型, 不同活动不同阶段的操作不同, 所以
 * @author Jeremy
 */
public abstract class AbstractActivityType implements IActivityType {
	
	protected final Activity activity;

	public AbstractActivityType(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public Activity getActivity() {
		return activity;
	}

	@Override
	public boolean isBegin() {
        return getActivity().getStatus() == BEGIN;
	}

	@Override
	public boolean isSettle() {
		return getActivity().getStatus() == SETTLE;
	}

	@Override
	public boolean isOpen() {
		int status = getActivity().getStatus();
		return status == BEGIN || status == SETTLE;
	}

	@Override
	public boolean isInCycle() {
		int status = getActivity().getStatus();
		return status == PREPARE || status == BEGIN || status == SETTLE;
	}

	@Override
	public void checkAndRefreshStatus( long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshConfig() {
		int configId = getActivity().getConfigId();
		if (configId <= 0) {
			return;
		}
		//TODO 刷新配置,数值覆盖到activity
	}

}
