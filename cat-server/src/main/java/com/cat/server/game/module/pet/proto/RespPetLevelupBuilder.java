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
* RespPetLevelupBuilder
* @author Jeremy
*/
public class RespPetLevelupBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespPetLevelupBuilder.class);
	
	private final RespPetLevelup.Builder builder = RespPetLevelup.newBuilder();
	
	public RespPetLevelupBuilder() {}
	
	public static RespPetLevelupBuilder newInstance() {
		return new RespPetLevelupBuilder();
	}
	
	public RespPetLevelup build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPetLevelup_VALUE;
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
