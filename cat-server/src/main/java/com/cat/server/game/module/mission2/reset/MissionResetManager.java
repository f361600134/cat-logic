package com.cat.server.game.module.mission2.reset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MissionResetManager {
	
	/**
	 * key: 目标类型
	 * value: 目标处理接口
	 */
	private Map<Integer, IMissionReset> missionResetMap = new HashMap<>();
	
	@Bean
	public void initMissionGoalMap(List<IMissionReset> missionResetList) {
		missionResetList.forEach(missionReset->{
			this.missionResetMap.put(missionReset.getResetType(), missionReset);
		});
	}


	/**
	 * 获取目标类型
	 * @param goalType
	 * @return
	 */
	public IMissionReset getMissionResetType(int missionResetType) {
		return missionResetMap.get(missionResetType);
	}
}
