package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* ReqPlayerHeartBuilder
* @author Jeremy
*/
public class ReqPlayerHeartBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqPlayerHeartBuilder.class);
	
	private final ReqPlayerHeart.Builder builder = ReqPlayerHeart.newBuilder();
	
	public ReqPlayerHeartBuilder() {}
	
	public static ReqPlayerHeartBuilder newInstance() {
		return new ReqPlayerHeartBuilder();
	}
	
	public ReqPlayerHeart build() {
		return builder.build();
	}
	
	/** 客户端收到的服务器流水号**/
	public void setRecvSequenceNo(int value){
		this.builder.setRecvSequenceNo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPlayerHeart_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
