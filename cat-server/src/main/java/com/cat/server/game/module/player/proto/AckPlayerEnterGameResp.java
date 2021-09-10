package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.AckPlayerEnterGame;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckPlayerEnterGameResp
* @author Jeremy
*/
public class AckPlayerEnterGameResp extends AbstractProtocol {

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
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
