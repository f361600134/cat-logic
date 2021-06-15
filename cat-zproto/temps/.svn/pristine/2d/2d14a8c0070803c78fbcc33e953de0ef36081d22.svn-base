package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* ReqPlayerLoginBuilder
* @author Jeremy
*/
public class ReqPlayerLoginBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqPlayerLoginBuilder.class);
	
	private final ReqPlayerLogin.Builder builder = ReqPlayerLogin.newBuilder();
	
	public ReqPlayerLoginBuilder() {}
	
	public static ReqPlayerLoginBuilder newInstance() {
		return new ReqPlayerLoginBuilder();
	}
	
	public ReqPlayerLogin build() {
		return builder.build();
	}
	
	/** 游戏账号**/
	public void setAccountName(String value){
		this.builder.setAccountName(value);
	}
	/** 渠道**/
	public void setChannel(int value){
		this.builder.setChannel(value);
	}
	/** 密匙**/
	public void setSessionKey(String value){
		this.builder.setSessionKey(value);
	}
	/** 游戏服id**/
	public void setServerId(int value){
		this.builder.setServerId(value);
	}
	/** 版本号**/
	public void setVersion(String value){
		this.builder.setVersion(value);
	}
	/** 登录服id**/
	public void setLoginSid(int value){
		this.builder.setLoginSid(value);
	}
	/** 设备码**/
	public void setDevice(String value){
		this.builder.setDevice(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPlayerLogin_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
