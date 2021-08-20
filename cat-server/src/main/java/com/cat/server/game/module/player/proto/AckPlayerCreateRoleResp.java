package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.PBItem.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* AckPlayerCreateRoleResp
* @author Jeremy
*/
public class AckPlayerCreateRoleResp implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckPlayerCreateRoleResp.class);
	
	private final AckPlayerCreateRole.Builder builder = AckPlayerCreateRole.newBuilder();
	
	public AckPlayerCreateRoleResp() {}
	
	public static AckPlayerCreateRoleResp newInstance() {
		return new AckPlayerCreateRoleResp();
	}
	
	public AckPlayerCreateRole build() {
		return builder.build();
	}
	
	/** **/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckPlayerCreateRole_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
