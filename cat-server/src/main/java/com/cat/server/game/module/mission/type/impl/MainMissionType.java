package com.cat.server.game.module.mission.type.impl;

import java.util.Map;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigMission;
import com.cat.server.game.module.mission.type.AbstractMission;

/**
 * 	任务类型-主线任务类, 单体任务类
 * @author Jeremy
 */
public class MainMissionType extends AbstractMission{

	public MainMissionType(int configId) {
		super(configId);
	}

	@Override
	public int getCompleteType() {
		return getConfig().getCompleteType();
	}

	@Override
	public int getCompleteCondition() {
		return getConfig().getCompleteValue();
	}

	@Override
	public int getCompleteValue() { 
		return getConfig().getCompleteTotal();
	}	
	
	@Override
	public Map<Integer, Integer> getReward() {
		return getConfig().getRewardMap();
	}

	public static MainMissionType create(int configId) {
		return new MainMissionType(configId);
	}
	
	private ConfigMission getConfig() {
		return ConfigManager.getInstance().getConfig(ConfigMission.class, configId);
	}
	
	@Override
	public String toString() {
		return "MainMissionType [configId=" + configId + ", state=" + state + "]";
	}
	
}
