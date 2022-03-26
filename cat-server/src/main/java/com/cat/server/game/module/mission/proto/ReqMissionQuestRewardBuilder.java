package com.cat.server.game.module.mission.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* ReqMissionQuestRewardBuilder
* @author Jeremy
*/
public class ReqMissionQuestRewardBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqMissionQuestRewardBuilder.class);
	
	private final ReqMissionQuestReward.Builder builder = ReqMissionQuestReward.newBuilder();
	
	public ReqMissionQuestRewardBuilder() {}
	
	public static ReqMissionQuestRewardBuilder newInstance() {
		return new ReqMissionQuestRewardBuilder();
	}
	
	public ReqMissionQuestReward build() {
		return builder.build();
	}
	
	/** mission id,-1表示一键领取**/
	public void setId(int value){
		this.builder.setId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqMissionQuestReward_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
