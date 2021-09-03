package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.AckPlayerHeart;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckPlayerHeartResp
* @author Jeremy
*/
public class AckPlayerHeartResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckPlayerHeartResp.class);
	
	private final AckPlayerHeart.Builder builder = AckPlayerHeart.newBuilder();
	
	public AckPlayerHeartResp() {}
	
	public static AckPlayerHeartResp newInstance() {
		return new AckPlayerHeartResp();
	}
	
	public AckPlayerHeart build() {
		return builder.build();
	}
	
	/** 服务器收到的客户端流水号**/
	public void setRecvSequenceNo(int value){
		this.builder.setRecvSequenceNo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckPlayerHeart_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
