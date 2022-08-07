package com.cat.server.game.module.functioncontrol.reddot.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cat.server.game.module.functioncontrol.define.ReddotConditionEnum;
import com.cat.server.game.module.functioncontrol.reddot.IFunctionReddot;
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
	public int getCondition() {
		return ReddotConditionEnum.LEARN_COMMUNITY_MISSION.getConditionId();
	}

	@Override
	public int checkReddot(long playerId) {
		QuestTypeData questTypeData = missionService.getQuestTypeData(playerId,MissionTypeEnum.LEARN_COMMUNITY);
		if (questTypeData == null) {
			return 0;
		}
		final Map<Integer, Quest> quests = questTypeData.getQuests();
		return (int)quests.values().stream().filter(c->c.getState()== QuestState.STATE_COMPLETE.getValue()).count();
	}

}
