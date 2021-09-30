package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespPlayerDisconnectBuilder
* @author Jeremy
*/
public class RespPlayerDisconnectBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespPlayerDisconnectBuilder.class);
	
	private final RespPlayerDisconnect.Builder builder = RespPlayerDisconnect.newBuilder();
	
	public RespPlayerDisconnectBuilder() {}
	
	public static RespPlayerDisconnectBuilder newInstance() {
		return new RespPlayerDisconnectBuilder();
	}
	
	public RespPlayerDisconnect build() {
		return builder.build();
	}
	
	/** 提示信息**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPlayerDisconnect_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
