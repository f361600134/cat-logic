package com.cat.server.game.module.mission.reset;

import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.handler.IQuestHandler;
import com.cat.server.utils.TimeUtil;

public abstract class AbstractQuestReset implements IQuestReset{
	
	@Override
	public boolean checkAndReset(long playerId, Quest quest, IQuestHandler<?> questHandler) {
		boolean bool = this.check(quest);
		if (bool) {
			this.reset(playerId, quest, questHandler);
		}
		return true;
	}
	
	/**
	 * 检测任务是否可以重置
	 * @param mission 任务对象
	 * @return
	 */
	protected abstract boolean check(Quest quest);
	
	/**
	 * 重置任务
	 * @param mission 任务对象
	 * @return
	 */
	protected void reset(long playerId, Quest quest, IQuestHandler<?> questHandler) {
		// 重置任务->1.取消,2.清除完成任务,3.接任务
		questHandler.abort(playerId, quest.getConfigId());
		questHandler.clear(playerId, quest.getConfigId());
		//重新接取任务并通知玩家
		questHandler.accept(playerId, quest.getConfigId(), TimeUtil.now());
	}

}
