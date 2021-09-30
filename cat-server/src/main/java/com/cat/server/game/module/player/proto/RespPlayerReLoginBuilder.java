package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespPlayerReLoginBuilder
* @author Jeremy
*/
public class RespPlayerReLoginBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespPlayerReLoginBuilder.class);
	
	private final RespPlayerReLogin.Builder builder = RespPlayerReLogin.newBuilder();
	
	public RespPlayerReLoginBuilder() {}
	
	public static RespPlayerReLoginBuilder newInstance() {
		return new RespPlayerReLoginBuilder();
	}
	
	public RespPlayerReLogin build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 服务器收到的客户端流水号**/
	public void setRecvSequenceNo(int value){
		this.builder.setRecvSequenceNo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPlayerReLogin_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
