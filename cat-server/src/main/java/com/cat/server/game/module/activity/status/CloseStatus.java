package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 活动结束状态
 * @author Jeremy
 */
public class CloseStatus extends AbstractStatus{
	
	public CloseStatus(IActivityType activityType) {
		super(activityType);
	}
	
	@Override
	public boolean checkNext(long now) {
		Activity activity = getActivity();
		if (activity.getStatus() != SETTLE) {
            return false;
        }
		long closeTime = activity.getCloseTime();
        if (now < closeTime) {
            return false;
        }
        return true;
	}

	@Override
	public void handle(long now) {
		Activity activity = getActivity();
        //先设置状态为关闭,再通知,最后清空活动数据
        activity.setStatus(getCode());
        activity.setConfigType(0);
		activity.setConfigId(0);
		activity.setPlanId(0);
		activity.setBeginTime(0);
		activity.setSettleTime(0);
		activity.setCloseTime(0);
		activity.save();
		activityType.onClose(now);
	}
	
	public int getCode() {
		return CLOSE;
	}
}
