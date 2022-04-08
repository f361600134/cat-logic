package com.cat.server.game.module.pet.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPet.*;
import java.util.Collection;

/**
* ReqPetIdentifyBuilder
* @author Jeremy
*/
public class ReqPetIdentifyBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqPetIdentifyBuilder.class);
	
	private final ReqPetIdentify.Builder builder = ReqPetIdentify.newBuilder();
	
	public ReqPetIdentifyBuilder() {}
	
	public static ReqPetIdentifyBuilder newInstance() {
		return new ReqPetIdentifyBuilder();
	}
	
	public ReqPetIdentify build() {
		return builder.build();
	}
	
	/** 宠物唯一id**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPetIdentify_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
