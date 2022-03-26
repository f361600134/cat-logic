package com.cat.server.game.module.mission.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleMultiDomain;

/**
* MissionDomain
* @author Jeremy
* @param Long 持有者id,这里是玩家id
* @param Integer 任务类型
*/
public class MissionDomain extends AbstractModuleMultiDomain<Long, Integer, Mission> {

	private static final Logger log = LoggerFactory.getLogger(MissionDomain.class);
	
	public MissionDomain(){
		
	}

	
	////////////业务代码////////////////////
	
	
	/**
	 * 获取任务数据
	 * @param questTypeId 任务类型
	 * @param createIfAbsent 不存在是否创建
	 * @return QuestTypeData  任务数据,可能为null
	 */
	public QuestTypeData getQuestTypeData(int questTypeId, boolean createIfAbsent) {
		Mission mission = this.getBean(questTypeId);
		if (mission == null && createIfAbsent) {
			mission = new Mission(this.getId());
			mission.setMissionType(questTypeId);
		}
		return mission.getQuestData();
	}
	
}
