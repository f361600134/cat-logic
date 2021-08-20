package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBCommon.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* PBPairInfoBuilder
* @author Jeremy
*/
public class PBPairInfoBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBPairInfoBuilder.class);
	
	private final PBPairInfo.Builder builder = PBPairInfo.newBuilder();
	
	public PBPairInfoBuilder() {
	}
	
	public static PBPairInfoBuilder newInstance() {
		return new PBPairInfoBuilder();
	}
	
	public PBPairInfo build() {
		return builder.build();
	}
	
	/** **/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** **/
	public void setValue(int value){
		this.builder.setValue(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
