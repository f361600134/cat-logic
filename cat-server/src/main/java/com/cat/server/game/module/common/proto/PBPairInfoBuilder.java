package com.cat.server.game.module.common.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBCommon.*;
import java.util.Collection;

/**
* PBPairInfoBuilder
* @author Jeremy
*/
public class PBPairInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBPairInfoBuilder.class);
	
	private final PBPairInfo.Builder builder = PBPairInfo.newBuilder();
	
	public PBPairInfoBuilder() {}
	
	public static PBPairInfoBuilder newInstance() {
		return new PBPairInfoBuilder();
	}
	
	public PBPairInfo build() {
		return builder.build();
	}
	
	/** 键**/
	public void setKey(int value){
		this.builder.setKey(value);
	}
	/** 值**/
	public void setValue(int value){
		this.builder.setValue(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return builder.build();
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
