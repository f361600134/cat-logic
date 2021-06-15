package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* AckPlayerLoginResp
* @author Jeremy
*/
public class AckPlayerLoginResp implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckPlayerLoginResp.class);
	
	private final AckPlayerLogin.Builder builder = AckPlayerLogin.newBuilder();
	
	public AckPlayerLoginResp() {}
	
	public static AckPlayerLoginResp newInstance() {
		return new AckPlayerLoginResp();
	}
	
	public AckPlayerLogin build() {
		return builder.build();
	}
	
	/** **/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 是否进入创建角色环节, 0=否,1=是**/
	public void setStatus(int value){
		this.builder.setStatus(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckPlayerLogin_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
