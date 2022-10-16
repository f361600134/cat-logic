package com.cat.server.game.module.activity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.server.game.data.proto.PBItem.PBRewardInfo;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBActivity.*;
import java.util.Collection;

/**
* RespActivityRankRewardBuilder
* @author Jeremy
*/
public class RespActivityRankRewardBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespActivityRankRewardBuilder.class);
	
	private final RespActivityRankReward.Builder builder = RespActivityRankReward.newBuilder();
	
	public RespActivityRankRewardBuilder() {}
	
	public static RespActivityRankRewardBuilder newInstance() {
		return new RespActivityRankRewardBuilder();
	}
	
	public RespActivityRankReward build() {
		return builder.build();
	}
	
	/** 排行奖励**/
	public void addRewards(PBRewardInfo value){
		this.builder.addRewards(value);
	}
	
	public void addAllRewards(Collection<PBRewardInfo> value){
		this.builder.addAllRewards(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespActivityRankReward_VALUE;
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
