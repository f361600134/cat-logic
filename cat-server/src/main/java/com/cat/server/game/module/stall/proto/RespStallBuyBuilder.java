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
* RespStallBuyBuilder
* @author Jeremy
*/
public class RespStallBuyBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespStallBuyBuilder.class);
	
	private final RespStallBuy.Builder builder = RespStallBuy.newBuilder();
	
	public RespStallBuyBuilder() {}
	
	public static RespStallBuyBuilder newInstance() {
		return new RespStallBuyBuilder();
	}
	
	public RespStallBuy build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespStallBuy_VALUE;
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
