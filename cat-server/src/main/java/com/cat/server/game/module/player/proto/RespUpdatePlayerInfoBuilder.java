package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespUpdatePlayerInfoBuilder
* @author Jeremy
*/
public class RespUpdatePlayerInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespUpdatePlayerInfoBuilder.class);
	
	private final RespUpdatePlayerInfo.Builder builder = RespUpdatePlayerInfo.newBuilder();
	
	public RespUpdatePlayerInfoBuilder() {}
	
	public static RespUpdatePlayerInfoBuilder newInstance() {
		return new RespUpdatePlayerInfoBuilder();
	}
	
	public RespUpdatePlayerInfo build() {
		return builder.build();
	}
	
	/** 玩家协议对象**/
	public void setPlayinfo(PBPlayerInfo value){
		this.builder.setPlayinfo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespUpdatePlayerInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
