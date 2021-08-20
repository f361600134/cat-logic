package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBCommon.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* AckItemUseResp
* @author Jeremy
*/
public class AckItemUseResp implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckItemUseResp.class);
	
	private final AckItemUse.Builder builder = AckItemUse.newBuilder();
	
	public AckItemUseResp() {}
	
	public static AckItemUseResp newInstance() {
		return new AckItemUseResp();
	}
	
	public AckItemUse build() {
		return builder.build();
	}
	
	/** **/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 奖励**/
	public void addRewards(PBRewardInfo value){
		this.builder.addRewards(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckItemUse_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
