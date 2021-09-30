package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespPlayerHeartBuilder
* @author Jeremy
*/
public class RespPlayerHeartBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespPlayerHeartBuilder.class);
	
	private final RespPlayerHeart.Builder builder = RespPlayerHeart.newBuilder();
	
	public RespPlayerHeartBuilder() {}
	
	public static RespPlayerHeartBuilder newInstance() {
		return new RespPlayerHeartBuilder();
	}
	
	public RespPlayerHeart build() {
		return builder.build();
	}
	
	/** 服务器收到的客户端流水号**/
	public void setRecvSequenceNo(int value){
		this.builder.setRecvSequenceNo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPlayerHeart_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
