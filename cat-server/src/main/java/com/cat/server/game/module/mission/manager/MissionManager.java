package com.cat.server.game.module.mission.manager;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.mission.domain.MissionDomain;
import com.cat.server.game.module.mission.goal.IQuestGoalType;
import com.cat.server.game.module.mission.reset.IQuestReset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @author Jeremy
*/
@Component
public class MissionManager extends AbstractModuleManager<Long, MissionDomain>{
	
	@Autowired private List<IQuestGoalType> missionGoalList;
	@Autowired private List<IQuestReset> questResetList;
	
	/**
	 * key: 目标类型
	 * value: 目标处理接口
	 */
	private Map<Integer, IQuestGoalType> missionGoalMap = new HashMap<>();
	private Map<Integer, IQuestReset> questResetMap = new HashMap<>();

	/**
	 * 获取目标类型
	 * @param goalType
	 * @return
	 */
	public IQuestGoalType getGoalType(int goalType) {
		return missionGoalMap.get(goalType);
	}
	
	/**
	 * 获取目标类型
	 * @param goalType
	 * @return
	 */
	public IQuestReset getQuestReset(int questResetType) {
		return questResetMap.get(questResetType);
	}

	@Override
	public void init() {
		missionGoalList.forEach(goalType->{
			this.missionGoalMap.put(goalType.getMissionGoal().getType(), goalType);
		});
		questResetList.forEach(questReset->{
			this.questResetMap.put(questReset.getResetType(), questReset);
		});
	}

}
