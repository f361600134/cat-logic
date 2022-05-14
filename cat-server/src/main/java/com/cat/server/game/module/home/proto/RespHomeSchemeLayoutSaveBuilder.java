package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* RespHomeSchemeLayoutSaveBuilder
* @author Jeremy
*/
public class RespHomeSchemeLayoutSaveBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespHomeSchemeLayoutSaveBuilder.class);
	
	private final RespHomeSchemeLayoutSave.Builder builder = RespHomeSchemeLayoutSave.newBuilder();
	
	public RespHomeSchemeLayoutSaveBuilder() {}
	
	public static RespHomeSchemeLayoutSaveBuilder newInstance() {
		return new RespHomeSchemeLayoutSaveBuilder();
	}
	
	public RespHomeSchemeLayoutSave build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespHomeSchemeLayoutSave_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
