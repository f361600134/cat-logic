package com.cat.server.game.module.mission.type.impl;

import java.util.Map;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigLearnCommunityMission;
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
		return getConfig().getGoalType();
	}

	@Override
	public int getCompleteCondition() {
		return getConfig().getGoalCondition();
	}

	@Override
	public int getCompleteValue() { 
		return getConfig().getGoalValue();
	}	
	
	@Override
	public Map<Integer, Integer> getReward() {
		return getConfig().getReward().getDictionary();
	}
	
	private ConfigLearnCommunityMission getConfig() {
		return ConfigManager.getInstance().getConfig(ConfigLearnCommunityMission.class, configId);
	}

	public static LearnCommunityMissionType create(int configId) {
		return new LearnCommunityMissionType(configId);
	}
	
	@Override
	public String toString() {
		return "LearnCommunityMissionType [configId=" + configId + ", state=" + state + "]";
	}
	
}
