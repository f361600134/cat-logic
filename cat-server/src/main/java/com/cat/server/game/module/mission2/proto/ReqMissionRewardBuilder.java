package com.cat.server.game.module.mission2.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* ReqMissionRewardBuilder
* @author Jeremy
*/
public class ReqMissionRewardBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqMissionRewardBuilder.class);
	
	private final ReqMissionReward.Builder builder = ReqMissionReward.newBuilder();
	
	public ReqMissionRewardBuilder() {}
	
	public static ReqMissionRewardBuilder newInstance() {
		return new ReqMissionRewardBuilder();
	}
	
	public ReqMissionReward build() {
		return builder.build();
	}
	
	/** mission id,-1表示一键领取**/
	public void setId(int value){
		this.builder.setId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqMissionReward_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
