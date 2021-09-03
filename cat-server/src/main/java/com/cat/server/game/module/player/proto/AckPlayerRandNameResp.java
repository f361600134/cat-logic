package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.AckPlayerRandName;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckPlayerRandNameResp
* @author Jeremy
*/
public class AckPlayerRandNameResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckPlayerRandNameResp.class);
	
	private final AckPlayerRandName.Builder builder = AckPlayerRandName.newBuilder();
	
	public AckPlayerRandNameResp() {}
	
	public static AckPlayerRandNameResp newInstance() {
		return new AckPlayerRandNameResp();
	}
	
	public AckPlayerRandName build() {
		return builder.build();
	}
	
	/** **/
	public void addNames(String value){
		this.builder.addNames(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckPlayerRandName_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
