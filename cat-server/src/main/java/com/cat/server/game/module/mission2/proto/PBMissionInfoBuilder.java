package com.cat.server.game.module.mission2.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* PBMissionInfoBuilder
* @author Jeremy
*/
public class PBMissionInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBMissionInfoBuilder.class);
	
	private final PBMissionInfo.Builder builder = PBMissionInfo.newBuilder();
	
	public PBMissionInfoBuilder() {}
	
	public static PBMissionInfoBuilder newInstance() {
		return new PBMissionInfoBuilder();
	}
	
	public PBMissionInfo build() {
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
	/** 任务目标i列表**/
	public void addGoals(PBMissionGoal value){
		this.builder.addGoals(value);
	}
	
	public void addAllGoals(Collection<PBMissionGoal> value){
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
