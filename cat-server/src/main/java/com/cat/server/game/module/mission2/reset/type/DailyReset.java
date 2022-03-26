package com.cat.server.game.module.mission2.reset.type;

import com.cat.server.game.module.mission2.reset.AbstractMissionReset;
import com.cat.server.game.module.mission2.type.Mission;
import com.cat.server.utils.TimeUtil;

/**
 * 每日重置
 * @author Jeremy
 */
public class DailyReset extends AbstractMissionReset{

	@Override
	public int getResetType() {
		return DAILY_RESET;
	}

	@Override
	protected boolean check(Mission mission) {
		return TimeUtil.isSameDay(mission.getAcceptTime(), TimeUtil.now());
	}

}
