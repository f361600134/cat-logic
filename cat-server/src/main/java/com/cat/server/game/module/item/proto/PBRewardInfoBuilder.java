package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBCommon.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* PBRewardInfoBuilder
* @author Jeremy
*/
public class PBRewardInfoBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBRewardInfoBuilder.class);
	
	private final PBRewardInfo.Builder builder = PBRewardInfo.newBuilder();
	
	public PBRewardInfoBuilder() {
	}
	
	public static PBRewardInfoBuilder newInstance() {
		return new PBRewardInfoBuilder();
	}
	
	public PBRewardInfo build() {
		return builder.build();
	}
	
	/** **/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** **/
	public void setCount(int value){
		this.builder.setCount(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
