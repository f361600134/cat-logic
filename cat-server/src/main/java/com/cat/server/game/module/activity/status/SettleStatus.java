package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 结算状态, 某些活动的一些奖励, 需要处于这个阶段发放<br>
 * 譬如, 竞赛积分结算, 排行奖励发放<br>
 * 如果不需要结算状态, 则无需设置settle持续时间值
 * @author Jeremy
 *
 */
public class SettleStatus extends AbstractStatus {

	public SettleStatus(IActivityType activityDomain) {
		super(activityDomain);
	}
	
	
	/**
	 * 当前结算状态, 检测进入下一个状态{@link CloseStatus}
	 */
	@Override
	public boolean checkNext(long now) {
		if (activityType.getStatus() != SETTLE) {
            return false;
        }
		//当前时间如果是领奖状态, 如果当前时间大于等于关闭时间, 则进入关闭状态
		long closeTime = activityType.getCloseTime();
        if (now < closeTime) {
            return false;
        }
        return true;
	}

	/**
	 * 到达结算阶段, 处理当前状态的逻辑.
	 */
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
