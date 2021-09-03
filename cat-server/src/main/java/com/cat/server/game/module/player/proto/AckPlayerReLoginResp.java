package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.AckPlayerReLogin;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckPlayerReLoginResp
* @author Jeremy
*/
public class AckPlayerReLoginResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckPlayerReLoginResp.class);
	
	private final AckPlayerReLogin.Builder builder = AckPlayerReLogin.newBuilder();
	
	public AckPlayerReLoginResp() {}
	
	public static AckPlayerReLoginResp newInstance() {
		return new AckPlayerReLoginResp();
	}
	
	public AckPlayerReLogin build() {
		return builder.build();
	}
	
	/** **/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 服务器收到的客户端流水号**/
	public void setRecvSequenceNo(int value){
		this.builder.setRecvSequenceNo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckPlayerReLogin_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
