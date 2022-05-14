package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* RespHomeFurnitureUpdateBuilder
* @author Jeremy
*/
public class RespHomeFurnitureUpdateBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespHomeFurnitureUpdateBuilder.class);
	
	private final RespHomeFurnitureUpdate.Builder builder = RespHomeFurnitureUpdate.newBuilder();
	
	public RespHomeFurnitureUpdateBuilder() {}
	
	public static RespHomeFurnitureUpdateBuilder newInstance() {
		return new RespHomeFurnitureUpdateBuilder();
	}
	
	public RespHomeFurnitureUpdate build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespHomeFurnitureUpdate_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
