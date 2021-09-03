package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.AckUpdatePlayerInfo;
import com.cat.server.game.data.proto.PBPlayer.PBPlayerInfo;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckUpdatePlayerInfoResp
* @author Jeremy
*/
public class AckUpdatePlayerInfoResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckUpdatePlayerInfoResp.class);
	
	private final AckUpdatePlayerInfo.Builder builder = AckUpdatePlayerInfo.newBuilder();
	
	public AckUpdatePlayerInfoResp() {}
	
	public static AckUpdatePlayerInfoResp newInstance() {
		return new AckUpdatePlayerInfoResp();
	}
	
	public AckUpdatePlayerInfo build() {
		return builder.build();
	}
	
	/** **/
	public void setPlayinfo(PBPlayerInfo value){
		this.builder.setPlayinfo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckUpdatePlayerInfo_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
