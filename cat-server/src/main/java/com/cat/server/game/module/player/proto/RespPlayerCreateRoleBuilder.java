package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespPlayerCreateRoleBuilder
* @author Jeremy
*/
public class RespPlayerCreateRoleBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespPlayerCreateRoleBuilder.class);
	
	private final RespPlayerCreateRole.Builder builder = RespPlayerCreateRole.newBuilder();
	
	public RespPlayerCreateRoleBuilder() {}
	
	public static RespPlayerCreateRoleBuilder newInstance() {
		return new RespPlayerCreateRoleBuilder();
	}
	
	public RespPlayerCreateRole build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPlayerCreateRole_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
