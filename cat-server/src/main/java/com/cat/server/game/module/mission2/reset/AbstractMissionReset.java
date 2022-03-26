package com.cat.server.game.module.mission2.reset;

import com.cat.server.game.module.mission2.handler.IMissionHandler;
import com.cat.server.game.module.mission2.type.Mission;
import com.cat.server.utils.TimeUtil;

public abstract class AbstractMissionReset implements IMissionReset{
	
	@Override
	public boolean checkAndReset(long playerId, Mission mission, IMissionHandler<?> missionHandler) {
		boolean bool = this.check(mission);
		if (bool) {
			this.reset(playerId, mission, missionHandler);
		}
		return true;
	}
	
	/**
	 * 检测任务是否可以重置
	 * @param mission 任务对象
	 * @return
	 */
	protected abstract boolean check(Mission mission);
	
	/**
	 * 重置任务
	 * @param mission 任务对象
	 * @return
	 */
	protected void reset(long playerId, Mission mission, IMissionHandler<?> missionHandler) {
		// 重置任务->1.取消,2.清除完成任务,3.接任务
		missionHandler.abort(playerId, mission.getConfigId());
		missionHandler.clear(playerId, mission.getConfigId());
		//重新接取任务并通知玩家
		missionHandler.accept(playerId, mission.getConfigId(), TimeUtil.now());
	}

}
