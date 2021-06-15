package com.cat.server.game.module.mission.type;


import java.util.Map;
import com.cat.server.game.data.config.ConfigMissionMgr;

/**
 * AbstractMission的派生类PlayerMissionType
 * @author Jeremy
 */
public class PlayerMissionType extends AbstractMission{

	public PlayerMissionType(int configId) {
		super(configId);
	}

	@Override
	public int getCompleteType() {
		//TODO ConfigXXXMgr.getConfig(configId).getCompleteType()
		return 0;
	}

	@Override
	public int getCompleteCondition() {
		//TODO ConfigXXXMgr.getConfig(configId).getCompleteValue()
		return 0;
	}

	@Override
	public int getCompleteValue() { 
		//TODO ConfigXXXMgr.getConfig(configId).getCompleteTotal()
		return 0;
	}	
	
	@Override
	public Map<Integer, Integer> getReward() {
		//TODO return ConfigXXXMgr.getConfig(configId).getRewardMap();
		return null;
	}

	public static PlayerMissionType create(int configId) {
		return new PlayerMissionType(configId);
	}
	
	@Override
	public String toString() {
		return "PlayerMissionType [configId=" + configId + ", state=" + state + "]";
	}
	
}
