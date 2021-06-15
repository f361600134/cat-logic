package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* AckInitPlayerInfoResp
* @author Jeremy
*/
public class AckInitPlayerInfoResp implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckInitPlayerInfoResp.class);
	
	private final AckInitPlayerInfo.Builder builder = AckInitPlayerInfo.newBuilder();
	
	public AckInitPlayerInfoResp() {}
	
	public static AckInitPlayerInfoResp newInstance() {
		return new AckInitPlayerInfoResp();
	}
	
	public AckInitPlayerInfo build() {
		return builder.build();
	}
	
	/** **/
	public void setPlayerInfo(PBPlayerInfo value){
		this.builder.setPlayerInfo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckInitPlayerInfo_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
