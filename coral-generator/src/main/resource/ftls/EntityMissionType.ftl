package com.cat.server.game.module.mission.type;


import java.util.Map;
import com.cat.server.game.data.config.ConfigMissionMgr;

/**
 * AbstractMission的派生类${entityName}MissionType
 * @author Jeremy
 */
public class ${entityName}MissionType extends AbstractMission{

	public ${entityName}MissionType(int configId) {
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

	public static ${entityName}MissionType create(int configId) {
		return new ${entityName}MissionType(configId);
	}
	
	@Override
	public String toString() {
		return "${entityName}MissionType [configId=" + configId + ", state=" + state + "]";
	}
	
}
