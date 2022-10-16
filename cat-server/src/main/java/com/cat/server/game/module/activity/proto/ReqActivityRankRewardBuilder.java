package com.cat.server.game.module.activity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBActivity.*;
import java.util.Collection;

/**
* ReqActivityRankRewardBuilder
* @author Jeremy
*/
public class ReqActivityRankRewardBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqActivityRankRewardBuilder.class);
	
	private final ReqActivityRankReward.Builder builder = ReqActivityRankReward.newBuilder();
	
	public ReqActivityRankRewardBuilder() {}
	
	public static ReqActivityRankRewardBuilder newInstance() {
		return new ReqActivityRankRewardBuilder();
	}
	
	public ReqActivityRankReward build() {
		return builder.build();
	}
	
	/** 活动类型id**/
	public void setActivityType(int value){
		this.builder.setActivityType(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqActivityRankReward_VALUE;
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return builder.build();
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
