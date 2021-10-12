package com.cat.server.game.module.activityoperation.learncommunity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBLearnCommunity.*;

/**
* ReqLearnCommunityRewardBuilder
* @author Jeremy
*/
public class ReqLearnCommunityRewardBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqLearnCommunityRewardBuilder.class);
	
	private final ReqLearnCommunityReward.Builder builder = ReqLearnCommunityReward.newBuilder();
	
	public ReqLearnCommunityRewardBuilder() {}
	
	public static ReqLearnCommunityRewardBuilder newInstance() {
		return new ReqLearnCommunityRewardBuilder();
	}
	
	public ReqLearnCommunityReward build() {
		return builder.build();
	}
	
	/** 领取的配置id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqLearnCommunityReward_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
