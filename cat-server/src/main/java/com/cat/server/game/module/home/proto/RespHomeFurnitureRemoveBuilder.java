package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* RespHomeFurnitureRemoveBuilder
* @author Jeremy
*/
public class RespHomeFurnitureRemoveBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespHomeFurnitureRemoveBuilder.class);
	
	private final RespHomeFurnitureRemove.Builder builder = RespHomeFurnitureRemove.newBuilder();
	
	public RespHomeFurnitureRemoveBuilder() {}
	
	public static RespHomeFurnitureRemoveBuilder newInstance() {
		return new RespHomeFurnitureRemoveBuilder();
	}
	
	public RespHomeFurnitureRemove build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespHomeFurnitureRemove_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
