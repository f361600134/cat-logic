package com.cat.server.game.module.mission.type.impl;

import java.util.Map;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigMissionLearnCommunity;
import com.cat.server.game.module.mission.type.AbstractMission;

/**
 * AbstractMission的派生类LearnCommunityMissionType
 * @author Jeremy
 */
public class LearnCommunityMissionType extends AbstractMission{

	public LearnCommunityMissionType(int configId) {
		super(configId);
	}

	@Override
	public int getCompleteType() {
//		return getConfig().getCompleteType();
		return 0;
	}

	@Override
	public int getCompleteCondition() {
//		return getConfig().getGoalCondition();
		return 0;
	}

	@Override
	public int getCompleteValue() { 
//		return getConfig().getGoalValue();
		return 0;
	}	
	
	@Override
	public Map<Integer, Integer> getReward() {
		return getConfig().getReward();
	}
	
	private ConfigMissionLearnCommunity getConfig() {
		return ConfigManager.getInstance().getConfig(ConfigMissionLearnCommunity.class, configId);
	}

	public static LearnCommunityMissionType create(int configId) {
		return new LearnCommunityMissionType(configId);
	}
	
	@Override
	public String toString() {
		return "LearnCommunityMissionType [configId=" + configId + ", state=" + state + "]";
	}
	
}
