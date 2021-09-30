package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespPlayerEnterGameBuilder
* @author Jeremy
*/
public class RespPlayerEnterGameBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespPlayerEnterGameBuilder.class);
	
	private final RespPlayerEnterGame.Builder builder = RespPlayerEnterGame.newBuilder();
	
	public RespPlayerEnterGameBuilder() {}
	
	public static RespPlayerEnterGameBuilder newInstance() {
		return new RespPlayerEnterGameBuilder();
	}
	
	public RespPlayerEnterGame build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.RespPlayerEnterGame_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
