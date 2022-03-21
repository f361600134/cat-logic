package com.cat.server.game.module.mission2.goal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GoalTypeManager {

	/**
	 * key: 目标类型
	 * value: 目标处理接口
	 */
	private Map<Integer, IMissionGoalType> missionGoalMap = new HashMap<>();

	@Bean
	public void initMissionGoalMap(List<IMissionGoalType> missionGoalList) {
		missionGoalList.forEach(goalType->{
			this.missionGoalMap.put(goalType.getMissionGoal().getType(), goalType);
		});
	}

	/**
	 * 获取目标类型
	 * @param goalType
	 * @return
	 */
	public IMissionGoalType getGoalType(int goalType) {
		return missionGoalMap.get(goalType);
	}

}