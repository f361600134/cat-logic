package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.IActivityType;

public class BeginStatus extends AbstractStatus {

	public BeginStatus(IActivityType activityDomain) {
		super(activityDomain);
	}

	@Override
	public boolean checkNext(long now) {
		Activity activity = getActivity();
		if (activity.getStatus() != CLOSE
				&& activity.getStatus() != PREPARE) {
			return false;
		}
		long beginTime = activity.getBeginTime();
		long settleTime = activity.getSettleTime();
		if (now < beginTime || now > settleTime) {
			return false;
		}
		return true;
	}
	
	@Override
	public void handle(long now) {
		super.handle(now);
        activityType.onBegin(now);
	}

	@Override
	public int getCode() {
		return BEGIN;
	}

}
