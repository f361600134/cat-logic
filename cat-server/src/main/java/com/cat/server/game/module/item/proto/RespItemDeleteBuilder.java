package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* RespItemDeleteBuilder
* @author Jeremy
*/
public class RespItemDeleteBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespItemDeleteBuilder.class);
	
	private final RespItemDelete.Builder builder = RespItemDelete.newBuilder();
	
	public RespItemDeleteBuilder() {}
	
	public static RespItemDeleteBuilder newInstance() {
		return new RespItemDeleteBuilder();
	}
	
	public RespItemDelete build() {
		return builder.build();
	}
	
	/** 奖励**/
	public void addIds(long value){
		this.builder.addIds(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespItemDelete_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
