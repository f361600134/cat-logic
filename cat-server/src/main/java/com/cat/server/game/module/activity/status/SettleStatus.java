package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.IActivityType;

public class SettleStatus extends AbstractStatus {

	public SettleStatus(IActivityType activityDomain) {
		super(activityDomain);
	}
	
	@Override
	public boolean checkNext(long now) {
		if (!activityType.isBegin()) {
            return false;
        }
		Activity activity = getActivity();
		long settleTime = activity.getSettleTime();
        if (now < settleTime) {
            return false;
        }
        return true;
	}

	@Override
	public void handle(long now) {
		super.handle(now);
        activityType.onSettle(now);
	}
	
	@Override
	public int getCode() {
		return SETTLE;
	}

}
