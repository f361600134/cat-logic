package com.cat.server.game.module.activity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBActivity.*;

/**
* ReqActivityInfoBuilder
* @author Jeremy
*/
public class ReqActivityInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqActivityInfoBuilder.class);
	
	private final ReqActivityInfo.Builder builder = ReqActivityInfo.newBuilder();
	
	public ReqActivityInfoBuilder() {}
	
	public static ReqActivityInfoBuilder newInstance() {
		return new ReqActivityInfoBuilder();
	}
	
	public ReqActivityInfo build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.ReqActivityInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
