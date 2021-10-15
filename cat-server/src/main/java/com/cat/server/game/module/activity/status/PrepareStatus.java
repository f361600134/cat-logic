package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 准备阶段, 此阶段玩家只可以预览活动信息, 什么都做不了.
 * @author Jeremy
 *
 */
public class PrepareStatus extends AbstractStatus{

	public PrepareStatus(IActivityType activityType) {
		super(activityType);
	}
	
	/**
	 * 准备阶段, 检测进入下一个状态{@link BeginStatus}
	 */
	@Override
	public boolean checkNext(long now) {
		if (activityType.getStatus() != PREPARE) {
			return false;
		}
		long beginTime = activityType.getBeginTime();
		long settleTime = activityType.getSettleTime();
		//如果当前时间,小于开始状态, 或者当前时间大于领奖时间, 不进入下一个状态
		if (now < beginTime || now > settleTime) {
			return false;
		}
        return true;
	}
	
	/**
	 * 到达准备阶段, 处理当前状态的逻辑
	 */
	@Override
	public void handle(long now) {
		super.handle(now);
        activityType.onPrepare(now);
	}

	@Override
	public int getCode() {
		return PREPARE;
	}

}
