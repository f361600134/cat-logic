package com.cat.server.game.module.pet.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPet.*;
import java.util.Collection;

/**
* RespPetIdentifyBuilder
* @author Jeremy
*/
public class RespPetIdentifyBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespPetIdentifyBuilder.class);
	
	private final RespPetIdentify.Builder builder = RespPetIdentify.newBuilder();
	
	public RespPetIdentifyBuilder() {}
	
	public static RespPetIdentifyBuilder newInstance() {
		return new RespPetIdentifyBuilder();
	}
	
	public RespPetIdentify build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPetIdentify_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
