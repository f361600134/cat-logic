package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.IActivityDomain;

/**
 * 活动结束状态
 * @author Jeremy
 */
public class CloseStatus extends AbstractStatus{
	
	public CloseStatus(IActivityDomain activityDomain) {
		super(activityDomain);
	}

	@Override
	public boolean handle(long now) {
		Activity activity = getActivity();
		if (activity.getStatus() != SETTLE) {
            return false;
        }
		long closeTime = activity.getCloseTime();
        if (now < closeTime) {
            return false;
        }
        //先设置状态为关闭,再通知,最后清空活动数据
        activity.setStatus(getCode());
        activity.setConfigType(0);
		activity.setConfigId(0);
		activity.setPlanId(0);
		activity.setBeginTime(0);
		activity.setSettleTime(0);
		activity.setCloseTime(0);
		activity.save();
        activityDomain.onClose(now);
		return true;
	}
	
	public int getCode() {
		return CLOSE;
	}
}
