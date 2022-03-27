package com.cat.server.game.module.mission.reset.type;

import org.springframework.stereotype.Component;
import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.reset.AbstractQuestReset;
import com.cat.server.utils.TimeUtil;

@Component
public class WeeklyReset extends AbstractQuestReset{

	@Override
	public int getResetType() {
		return WEEKLY_RESET;
	}

	@Override
	protected boolean check(Quest quest) {
		return TimeUtil.isSameWeek(quest.getAcceptTime(), TimeUtil.now());
	}

}
