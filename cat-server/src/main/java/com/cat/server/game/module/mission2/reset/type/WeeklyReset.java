package com.cat.server.game.module.mission2.reset.type;

import com.cat.server.game.module.mission2.reset.AbstractMissionReset;
import com.cat.server.game.module.mission2.type.Mission;
import com.cat.server.utils.TimeUtil;

public class WeeklyReset extends AbstractMissionReset{

	@Override
	public int getResetType() {
		return WEEKLY_RESET;
	}

	@Override
	protected boolean check(Mission mission) {
		return TimeUtil.isSameWeek(mission.getAcceptTime(), TimeUtil.now());
	}

}
