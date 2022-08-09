package com.cat.server.game.module.function.reddot.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.game.module.function.define.ReddotTypeEnum;
import com.cat.server.game.module.function.reddot.IFunctionReddot;
import com.cat.server.game.module.mission.IMissionService;
import com.cat.server.game.module.mission.define.MissionTypeEnum;
import com.cat.server.game.module.mission.define.QuestState;
import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.domain.QuestTypeData;

/**
 * 红点条件-研习社活动
 * @author Jeremy
 */
@Component
public class ReddotLearnCommunityMission implements IFunctionReddot {
	
	@Autowired private IMissionService missionService;

	@Override
	public int getReddotId() {
		return ReddotTypeEnum.LEARN_COMMUNITY_MISSION.getReddotId();
	}

	@Override
	public List<Integer> checkReddot(long playerId) {
		QuestTypeData questTypeData = missionService.getQuestTypeData(playerId,MissionTypeEnum.LEARN_COMMUNITY);
		if (questTypeData == null) {
			return Collections.emptyList();
		}
		final Map<Integer, Quest> quests = questTypeData.getQuests();
		int count = (int)quests.values().stream().filter(c->c.getState()== QuestState.STATE_COMPLETE.getValue()).count();
		return Collections.singletonList(count);
	}

}
