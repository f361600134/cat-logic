package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespPlayerLoginBuilder
* @author Jeremy
*/
public class RespPlayerLoginBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespPlayerLoginBuilder.class);
	
	private final RespPlayerLogin.Builder builder = RespPlayerLogin.newBuilder();
	
	public RespPlayerLoginBuilder() {}
	
	public static RespPlayerLoginBuilder newInstance() {
		return new RespPlayerLoginBuilder();
	}
	
	public RespPlayerLogin build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 是否进入创建角色环节, 0=否,1=是**/
	public void setStatus(int value){
		this.builder.setStatus(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPlayerLogin_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
