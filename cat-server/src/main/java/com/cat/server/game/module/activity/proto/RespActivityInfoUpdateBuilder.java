package com.cat.server.game.module.activity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBActivity.*;

/**
* RespActivityInfoUpdateBuilder
* @author Jeremy
*/
public class RespActivityInfoUpdateBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespActivityInfoUpdateBuilder.class);
	
	private final RespActivityInfoUpdate.Builder builder = RespActivityInfoUpdate.newBuilder();
	
	public RespActivityInfoUpdateBuilder() {}
	
	public static RespActivityInfoUpdateBuilder newInstance() {
		return new RespActivityInfoUpdateBuilder();
	}
	
	public RespActivityInfoUpdate build() {
		return builder.build();
	}
	
	/** 活动信息**/
	public void setActivity(PBActivityInfo value){
		this.builder.setActivity(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespActivityInfoUpdate_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
