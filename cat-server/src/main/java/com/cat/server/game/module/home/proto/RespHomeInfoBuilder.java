package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* RespHomeInfoBuilder
* @author Jeremy
*/
public class RespHomeInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespHomeInfoBuilder.class);
	
	private final RespHomeInfo.Builder builder = RespHomeInfo.newBuilder();
	
	public RespHomeInfoBuilder() {}
	
	public static RespHomeInfoBuilder newInstance() {
		return new RespHomeInfoBuilder();
	}
	
	public RespHomeInfo build() {
		return builder.build();
	}
	
	/** 家园信息**/
	public void setHome(PBHomeInfo value){
		this.builder.setHome(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespHomeInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
