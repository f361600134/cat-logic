package com.cat.server.game.module.pet.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPet.*;
import java.util.Collection;

/**
* ReqPetActiveBuilder
* @author Jeremy
*/
public class ReqPetActiveBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqPetActiveBuilder.class);
	
	private final ReqPetActive.Builder builder = ReqPetActive.newBuilder();
	
	public ReqPetActiveBuilder() {}
	
	public static ReqPetActiveBuilder newInstance() {
		return new ReqPetActiveBuilder();
	}
	
	public ReqPetActive build() {
		return builder.build();
	}
	
	/** 宠物唯一id**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPetActive_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
