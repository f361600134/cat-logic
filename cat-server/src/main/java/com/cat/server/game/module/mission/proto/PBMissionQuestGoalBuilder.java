package com.cat.server.game.module.mission.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* PBMissionQuestGoalBuilder
* @author Jeremy
*/
public class PBMissionQuestGoalBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBMissionQuestGoalBuilder.class);
	
	private final PBMissionQuestGoal.Builder builder = PBMissionQuestGoal.newBuilder();
	
	public PBMissionQuestGoalBuilder() {}
	
	public static PBMissionQuestGoalBuilder newInstance() {
		return new PBMissionQuestGoalBuilder();
	}
	
	public PBMissionQuestGoal build() {
		return builder.build();
	}
	
	/** 任务类型索引**/
	public void setIndex(int value){
		this.builder.setIndex(value);
	}
	/** 任务目标类型**/
	public void setType(int value){
		this.builder.setType(value);
	}
	/** 任务目标进度**/
	public void setProgress(long value){
		this.builder.setProgress(value);
	}
	/** 任务状态**/
	public void setState(int value){
		this.builder.setState(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
