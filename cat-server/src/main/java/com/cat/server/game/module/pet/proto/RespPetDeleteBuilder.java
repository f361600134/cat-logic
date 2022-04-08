package com.cat.server.game.module.pet.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPet.*;
import java.util.Collection;

/**
* RespPetDeleteBuilder
* @author Jeremy
*/
public class RespPetDeleteBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespPetDeleteBuilder.class);
	
	private final RespPetDelete.Builder builder = RespPetDelete.newBuilder();
	
	public RespPetDeleteBuilder() {}
	
	public static RespPetDeleteBuilder newInstance() {
		return new RespPetDeleteBuilder();
	}
	
	public RespPetDelete build() {
		return builder.build();
	}
	
	/** 宠物唯一id**/
	public void addUniqueId(long value){
		this.builder.addUniqueId(value);
	}
	
	public void addAllUniqueId(Collection<java.lang.Long> value){
		this.builder.addAllUniqueId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPetDelete_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
