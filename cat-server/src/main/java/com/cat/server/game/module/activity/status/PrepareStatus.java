package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.IActivityType;

public class PrepareStatus extends AbstractStatus{

	public PrepareStatus(IActivityType activityType) {
		super(activityType);
	}

	@Override
	public boolean handle(long now) {
		Activity activity = getActivity();
		if (activity.getStatus() != CLOSE) {
            return false;
        }
        if (now >= activity.getBeginTime()) {
            // 活动已到开始时间 不再尝试进入准备阶段
            return false;
        }
        //先设置状态为关闭,再通知,最后清空活动数据
        activity.setStatus(getCode());
        activity.save();
        
        activityType.onPrepare(now);
        return true;
	}

	@Override
	public int getCode() {
		return PREPARE;
	}

}
