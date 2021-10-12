package com.cat.server.game.module.activityoperation.learncommunity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBLearnCommunity.*;

/**
* PBLearnCommunityRewardBuilder
* @author Jeremy
*/
public class PBLearnCommunityRewardBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBLearnCommunityRewardBuilder.class);
	
	private final PBLearnCommunityReward.Builder builder = PBLearnCommunityReward.newBuilder();
	
	public PBLearnCommunityRewardBuilder() {}
	
	public static PBLearnCommunityRewardBuilder newInstance() {
		return new PBLearnCommunityRewardBuilder();
	}
	
	public PBLearnCommunityReward build() {
		return builder.build();
	}
	
	/** 奖励配置id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** 普通奖励状态,0未达成,1已达成未领取,2已领取**/
	public void setStatus(int value){
		this.builder.setStatus(value);
	}
	/** 专属奖励状态,0未达成,1已达成未领取,2已领取**/
	public void setExclusiveStatus(int value){
		this.builder.setExclusiveStatus(value);
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
