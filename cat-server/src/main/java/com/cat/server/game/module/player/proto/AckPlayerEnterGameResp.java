package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.PBItem.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* AckPlayerEnterGameResp
* @author Jeremy
*/
public class AckPlayerEnterGameResp implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckPlayerEnterGameResp.class);
	
	private final AckPlayerEnterGame.Builder builder = AckPlayerEnterGame.newBuilder();
	
	public AckPlayerEnterGameResp() {}
	
	public static AckPlayerEnterGameResp newInstance() {
		return new AckPlayerEnterGameResp();
	}
	
	public AckPlayerEnterGame build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.AckPlayerEnterGame_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
