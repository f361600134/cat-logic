package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 开始状态, 活动正式开始, 玩家可以操作,领取奖励等
 * @author Jeremy
 */
public class BeginStatus extends AbstractStatus {

	public BeginStatus(IActivityType activityDomain) {
		super(activityDomain);
	}

	/**
	 * 当前是开始状态, 检测进入下一个状态{@link SettleStatus}
	 */
	@Override
	public boolean checkNext(long now) {
		if (!activityType.isBegin()) {
            return false;
        }
		long settleTime = activityType.getSettleTime();
        if (now < settleTime) {
            return false;
        }
		return true;
	}
	
	/**
	 * 到达开始阶段, 处理当前状态的逻辑
	 */
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
