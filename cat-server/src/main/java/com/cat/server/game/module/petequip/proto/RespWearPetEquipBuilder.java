package com.cat.server.game.module.petequip.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBPetEquip.*;
import java.util.Collection;

/**
* RespWearPetEquipBuilder
* @author Jeremy
*/
public class RespWearPetEquipBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespWearPetEquipBuilder.class);
	
	private final RespWearPetEquip.Builder builder = RespWearPetEquip.newBuilder();
	
	public RespWearPetEquipBuilder() {}
	
	public static RespWearPetEquipBuilder newInstance() {
		return new RespWearPetEquipBuilder();
	}
	
	public RespWearPetEquip build() {
		return builder.build();
	}
	
	/** errorCode**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespWearPetEquip_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
