package com.cat.server.game.module.mission.reset.type;

import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.handler.IQuestHandler;
import com.cat.server.game.module.mission.reset.IQuestReset;

/**
 * 不重置
 * @author Jeremy
 */
public class NotReset implements IQuestReset{

	@Override
	public int getResetType() {
		return NOT_RESET;
	}

	@Override
	public boolean checkAndReset(long playerId, Quest quest, IQuestHandler<?> questHandler) {
		return false;
	}

}
