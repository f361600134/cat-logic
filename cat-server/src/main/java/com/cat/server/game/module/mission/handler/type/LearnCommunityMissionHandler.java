package com.cat.server.game.module.mission.handler.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cat.server.game.data.config.local.ConfigMissionLearnCommunity;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.module.activity.type.impl.LearnCommunityActivityType;
import com.cat.server.game.module.activityoperation.learncommunity.ILearnCommunityService;
import com.cat.server.game.module.mission.define.MissionTypeEnum;
import com.cat.server.game.module.mission.domain.QuestTypeData;
import com.cat.server.game.module.mission.handler.AbstractActivityMissionHandler;


/**
 * 研习社实现类
 * @author Jeremy
 */
@Component
public class LearnCommunityMissionHandler extends AbstractActivityMissionHandler<ConfigMissionLearnCommunity, LearnCommunityActivityType>{
	
	@Autowired private ILearnCommunityService learnCommunityService;
	
	@Override
	public MissionTypeEnum getMissionType() {
		return MissionTypeEnum.LEARN_COMMUNITY;
	}
	
	@Override
	public QuestTypeData getQuestTypeData(long playerId, boolean createIfAbsent) {
		return learnCommunityService.getQuestTypeData(playerId);
	}

	@Override
	public int getModuleId() {
		return ModuleDefine.LEARNCOMMUNITY_MISSION.getModuleId();
	}
}
