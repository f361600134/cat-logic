
package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.AckPlayerDisconnect;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckPlayerDisconnectResp
* @author Jeremy
*/
public class AckPlayerDisconnectResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckPlayerDisconnectResp.class);
	
	private final AckPlayerDisconnect.Builder builder = AckPlayerDisconnect.newBuilder();
	
	public AckPlayerDisconnectResp() {}
	
	public static AckPlayerDisconnectResp newInstance() {
		return new AckPlayerDisconnectResp();
	}
	
	public AckPlayerDisconnect build() {
		return builder.build();
	}
	
	/** 提示信息**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckPlayerDisconnect_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
