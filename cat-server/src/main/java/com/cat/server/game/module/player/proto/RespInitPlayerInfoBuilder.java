package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespInitPlayerInfoBuilder
* @author Jeremy
*/
public class RespInitPlayerInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespInitPlayerInfoBuilder.class);
	
	private final RespInitPlayerInfo.Builder builder = RespInitPlayerInfo.newBuilder();
	
	public RespInitPlayerInfoBuilder() {}
	
	public static RespInitPlayerInfoBuilder newInstance() {
		return new RespInitPlayerInfoBuilder();
	}
	
	public RespInitPlayerInfo build() {
		return builder.build();
	}
	
	/** 玩家协议对象**/
	public void setPlayerInfo(PBPlayerInfo value){
		this.builder.setPlayerInfo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespInitPlayerInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
