package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.IActivityDomain;

public class BeginStatus extends AbstractStatus {

	public BeginStatus(IActivityDomain activityDomain) {
		super(activityDomain);
	}

	@Override
	public boolean handle(long now) {
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
		//先设置状态,再通知,最后清空活动数据
        activity.setStatus(getCode());
        activity.save();
        activityDomain.onBegin(now);
		return true;
	}

	@Override
	public int getCode() {
		return BEGIN;
	}

}
