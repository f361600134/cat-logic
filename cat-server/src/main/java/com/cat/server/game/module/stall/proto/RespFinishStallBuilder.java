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
* RespFinishStallBuilder
* @author Jeremy
*/
public class RespFinishStallBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespFinishStallBuilder.class);
	
	private final RespFinishStall.Builder builder = RespFinishStall.newBuilder();
	
	public RespFinishStallBuilder() {}
	
	public static RespFinishStallBuilder newInstance() {
		return new RespFinishStallBuilder();
	}
	
	public RespFinishStall build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespFinishStall_VALUE;
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
