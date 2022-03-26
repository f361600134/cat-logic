package com.cat.server.game.module.mission.domain;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence; 

/**
* @author Jeremy
*/
@PO(name = "mission")
public class Mission extends MissionPo implements IPersistence{
	
	@Column(PROP_MISSIONSTR)
	private QuestTypeData questData = new QuestTypeData();
	
	public QuestTypeData getQuestData() {
		return questData;
	}

	public void setQuestData(QuestTypeData questData) {
		this.questData = questData;
	}

	public Mission() {

	}
	
	public Mission(long playerId) {
		this.playerId = playerId;
	}
	
}
