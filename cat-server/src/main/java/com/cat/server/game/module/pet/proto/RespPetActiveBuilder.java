package com.cat.server.game.module.pet.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPet.*;
import com.google.protobuf.AbstractMessage;

import java.util.Collection;

/**
* RespPetActiveBuilder
* @author Jeremy
*/
public class RespPetActiveBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespPetActiveBuilder.class);
	
	private final RespPetActive.Builder builder = RespPetActive.newBuilder();
	
	public RespPetActiveBuilder() {}
	
	public static RespPetActiveBuilder newInstance() {
		return new RespPetActiveBuilder();
	}
	
	public RespPetActive build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPetActive_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return this.build();
	}
}
