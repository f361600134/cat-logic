package com.cat.server.game.module.mission2.reset.type;

import com.cat.server.game.module.mission2.handler.IMissionHandler;
import com.cat.server.game.module.mission2.reset.IMissionReset;
import com.cat.server.game.module.mission2.type.Mission;

/**
 * 不重置
 * @author Jeremy
 */
public class NotReset implements IMissionReset{

	@Override
	public int getResetType() {
		return NOT_RESET;
	}

	@Override
	public boolean checkAndReset(long playerId, Mission mission, IMissionHandler<?> missionHandler) {
		return false;
	}

}
