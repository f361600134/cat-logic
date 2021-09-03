package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.AckInitPlayerInfo;
import com.cat.server.game.data.proto.PBPlayer.PBPlayerInfo;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckInitPlayerInfoResp
* @author Jeremy
*/
public class AckInitPlayerInfoResp extends AbstractProtocol {

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
