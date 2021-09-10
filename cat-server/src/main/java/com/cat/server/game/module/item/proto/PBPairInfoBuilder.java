package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBItem.PBPairInfo;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* PBPairInfoBuilder
* @author Jeremy
*/
public class PBPairInfoBuilder extends AbstractProtocol {

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
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
