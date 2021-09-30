package com.cat.server.game.module.activity.status;

import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 活动关闭状态, 到达此状态, 活动关闭. 客户端无法看见此活动信息
 * @author Jeremy
 */
public class CloseStatus extends AbstractStatus{
	
	public CloseStatus(IActivityType activityType) {
		super(activityType);
	}
	
	/**
	 * 当前是活动结束状态, 检测进入下一个状态-> {@link PrepareStatus}
	 */
	@Override
	public boolean checkNext(long now) {
		if (activityType.getStatus() != CLOSE) {
            return false;
        }
		// 活动已经到准备时间, 并且没有到领奖时间, 活动可以进入准备状态
        if (now >= activityType.getPrepareTime() 
        		&& now < activityType.getSettleTime()) {
            return true;
        }
        return false;
	}

	/**
	 * 到达关闭阶段, 处理当前状态的逻辑
	 */
	@Override
	public void doHandle(long now) {
		activityType.onClose(now);
	}
	
	public int getCode() {
		return CLOSE;
	}
}
