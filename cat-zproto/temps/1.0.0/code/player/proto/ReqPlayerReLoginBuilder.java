package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* ReqPlayerReLoginBuilder
* @author Jeremy
*/
public class ReqPlayerReLoginBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqPlayerReLoginBuilder.class);
	
	private final ReqPlayerReLogin.Builder builder = ReqPlayerReLogin.newBuilder();
	
	public ReqPlayerReLoginBuilder() {}
	
	public static ReqPlayerReLoginBuilder newInstance() {
		return new ReqPlayerReLoginBuilder();
	}
	
	public ReqPlayerReLogin build() {
		return builder.build();
	}
	
	/** **/
	public void setUserName(String value){
		this.builder.setUserName(value);
	}
	/** **/
	public void setChannel(int value){
		this.builder.setChannel(value);
	}
	/** **/
	public void setSessionKey(String value){
		this.builder.setSessionKey(value);
	}
	/** 客户端收到的服务器流水号**/
	public void setRecvSequenceNo(int value){
		this.builder.setRecvSequenceNo(value);
	}
	/** 登陆时所选游戏服id**/
	public void setServerId(int value){
		this.builder.setServerId(value);
	}
	/** 版本号**/
	public void setVersion(String value){
		this.builder.setVersion(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPlayerReLogin_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
