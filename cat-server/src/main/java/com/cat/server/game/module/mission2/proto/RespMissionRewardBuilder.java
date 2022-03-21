package com.cat.server.game.module.mission2.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.server.game.data.proto.PBItem.PBRewardInfo;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* RespMissionRewardBuilder
* @author Jeremy
*/
public class RespMissionRewardBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespMissionRewardBuilder.class);
	
	private final RespMissionReward.Builder builder = RespMissionReward.newBuilder();
	
	public RespMissionRewardBuilder() {}
	
	public static RespMissionRewardBuilder newInstance() {
		return new RespMissionRewardBuilder();
	}
	
	public RespMissionReward build() {
		return builder.build();
	}
	
	/** 错误码,非0表示弹提示**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 奖励**/
	public void addRewards(PBRewardInfo value){
		this.builder.addRewards(value);
	}
	
	public void addAllRewards(Collection<PBRewardInfo> value){
		this.builder.addAllRewards(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespMissionReward_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
