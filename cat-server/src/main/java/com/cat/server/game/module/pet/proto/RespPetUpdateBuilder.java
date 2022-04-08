package com.cat.server.game.module.pet.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPet.*;
import java.util.Collection;

/**
* RespPetUpdateBuilder
* @author Jeremy
*/
public class RespPetUpdateBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespPetUpdateBuilder.class);
	
	private final RespPetUpdate.Builder builder = RespPetUpdate.newBuilder();
	
	public RespPetUpdateBuilder() {}
	
	public static RespPetUpdateBuilder newInstance() {
		return new RespPetUpdateBuilder();
	}
	
	public RespPetUpdate build() {
		return builder.build();
	}
	
	/** 宠物列表**/
	public void addPets(PBPetDto value){
		this.builder.addPets(value);
	}
	
	public void addAllPets(Collection<PBPetDto> value){
		this.builder.addAllPets(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPetUpdate_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
