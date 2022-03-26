package com.cat.server.game.module.mission.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* PBMissionQuestInfoBuilder
* @author Jeremy
*/
public class PBMissionQuestInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBMissionQuestInfoBuilder.class);
	
	private final PBMissionQuestInfo.Builder builder = PBMissionQuestInfo.newBuilder();
	
	public PBMissionQuestInfoBuilder() {}
	
	public static PBMissionQuestInfoBuilder newInstance() {
		return new PBMissionQuestInfoBuilder();
	}
	
	public PBMissionQuestInfo build() {
		return builder.build();
	}
	
	/** 任务配置id**/
	public void setId(int value){
		this.builder.setId(value);
	}
	/** 任务类型 1主线 2剧情 3支线 4成就 5日常**/
	public void setType(int value){
		this.builder.setType(value);
	}
	/** 任务目标列表**/
	public void addGoals(PBMissionQuestGoal value){
		this.builder.addGoals(value);
	}
	
	public void addAllGoals(Collection<PBMissionQuestGoal> value){
		this.builder.addAllGoals(value);
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
