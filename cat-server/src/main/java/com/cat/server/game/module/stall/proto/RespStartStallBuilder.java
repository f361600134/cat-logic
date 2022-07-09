package com.cat.server.game.module.stall.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBStall.*;
import java.util.Collection;

/**
* RespStartStallBuilder
* @author Jeremy
*/
public class RespStartStallBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespStartStallBuilder.class);
	
	private final RespStartStall.Builder builder = RespStartStall.newBuilder();
	
	public RespStartStallBuilder() {}
	
	public static RespStartStallBuilder newInstance() {
		return new RespStartStallBuilder();
	}
	
	public RespStartStall build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespStartStall_VALUE;
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
