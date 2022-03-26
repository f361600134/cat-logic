package com.cat.server.game.module.mission.reset.type;

import org.springframework.stereotype.Component;

import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.reset.AbstractQuestReset;
import com.cat.server.utils.TimeUtil;

/**
 * 每日重置
 * @author Jeremy
 */
@Component
public class DailyReset extends AbstractQuestReset{

	@Override
	public int getResetType() {
		return DAILY_RESET;
	}

	@Override
	protected boolean check(Quest quest) {
		return TimeUtil.isSameDay(quest.getAcceptTime(), TimeUtil.now());
	}

}
