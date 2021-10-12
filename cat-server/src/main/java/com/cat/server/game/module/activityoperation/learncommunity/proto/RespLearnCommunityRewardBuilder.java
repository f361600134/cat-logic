package com.cat.server.game.module.activityoperation.learncommunity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.server.game.data.proto.PBItem.PBPairInfo;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBLearnCommunity.*;

/**
* RespLearnCommunityRewardBuilder
* @author Jeremy
*/
public class RespLearnCommunityRewardBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespLearnCommunityRewardBuilder.class);
	
	private final RespLearnCommunityReward.Builder builder = RespLearnCommunityReward.newBuilder();
	
	public RespLearnCommunityRewardBuilder() {}
	
	public static RespLearnCommunityRewardBuilder newInstance() {
		return new RespLearnCommunityRewardBuilder();
	}
	
	public RespLearnCommunityReward build() {
		return builder.build();
	}
	
	/** 领取的配置id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** 奖励内容**/
	public void addRewards(PBPairInfo value){
		this.builder.addRewards(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespLearnCommunityReward_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
