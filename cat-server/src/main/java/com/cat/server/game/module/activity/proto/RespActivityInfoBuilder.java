package com.cat.server.game.module.activity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
import com.google.protobuf.AbstractMessage;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBActivity.*;

/**
* RespActivityInfoBuilder
* @author Jeremy
*/
public class RespActivityInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespActivityInfoBuilder.class);
	
	private final RespActivityInfo.Builder builder = RespActivityInfo.newBuilder();
	
	public RespActivityInfoBuilder() {}
	
	public static RespActivityInfoBuilder newInstance() {
		return new RespActivityInfoBuilder();
	}
	
	public RespActivityInfo build() {
		return builder.build();
	}
	
	/** 活动列表**/
	public void addActivitys(PBActivityInfo value){
		this.builder.addActivitys(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespActivityInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return builder.build();
	}
}
