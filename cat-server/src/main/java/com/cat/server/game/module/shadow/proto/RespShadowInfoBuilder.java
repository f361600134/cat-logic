package com.cat.server.game.module.shadow.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.PBPlayerProfile;
import com.cat.server.game.data.proto.PBShadow.RespShadowInfo;

/**
* RespShadowInfoBuilder
* @author Jeremy
*/
public class RespShadowInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespShadowInfoBuilder.class);
	
	private final RespShadowInfo.Builder builder = RespShadowInfo.newBuilder();
	
	public RespShadowInfoBuilder() {}
	
	public static RespShadowInfoBuilder newInstance() {
		return new RespShadowInfoBuilder();
	}
	
	public RespShadowInfo build() {
		return builder.build();
	}
	
	/** 玩家简要信息**/
	public void setPlayerInfo(PBPlayerProfile value){
		this.builder.setPlayerInfo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespShadowInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
