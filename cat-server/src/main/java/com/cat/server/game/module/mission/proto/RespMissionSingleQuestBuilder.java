package com.cat.server.game.module.mission.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* RespMissionSingleQuestBuilder
* @author Jeremy
*/
public class RespMissionSingleQuestBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespMissionSingleQuestBuilder.class);
	
	private final RespMissionSingleQuest.Builder builder = RespMissionSingleQuest.newBuilder();
	
	public RespMissionSingleQuestBuilder() {}
	
	public static RespMissionSingleQuestBuilder newInstance() {
		return new RespMissionSingleQuestBuilder();
	}
	
	public RespMissionSingleQuest build() {
		return builder.build();
	}
	
	/** 任务数据**/
	public void setQuest(PBMissionQuestInfo value){
		this.builder.setQuest(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespMissionSingleQuest_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
