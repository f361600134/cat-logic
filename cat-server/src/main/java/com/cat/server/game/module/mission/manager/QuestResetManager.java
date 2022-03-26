package com.cat.server.game.module.mission.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.cat.server.game.module.mission.reset.IQuestReset;

@Component
public class QuestResetManager {
	
	/**
	 * key: 目标类型
	 * value: 目标处理接口
	 */
	private Map<Integer, IQuestReset> questResetMap = new HashMap<>();
	
	@Bean
	public void initMissionGoalMap(List<IQuestReset> questResetList) {
		questResetList.forEach(missionReset->{
			this.questResetMap.put(missionReset.getResetType(), missionReset);
		});
	}

	/**
	 * 获取目标类型
	 * @param goalType
	 * @return
	 */
	public IQuestReset getMissionResetType(int questResetType) {
		return questResetMap.get(questResetType);
	}
}
