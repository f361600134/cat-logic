package com.cat.server.game.module.petequip.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBPetEquip.*;
import java.util.Collection;

/**
* PBPetEquipDtoBuilder
* @author Jeremy
*/
public class PBPetEquipDtoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBPetEquipDtoBuilder.class);
	
	private final PBPetEquipDto.Builder builder = PBPetEquipDto.newBuilder();
	
	public PBPetEquipDtoBuilder() {}
	
	public static PBPetEquipDtoBuilder newInstance() {
		return new PBPetEquipDtoBuilder();
	}
	
	public PBPetEquipDto build() {
		return builder.build();
	}
	
	/** 装备唯一id**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
	}
	/** 装备配置id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** 持有者id**/
	public void setHolderId(long value){
		this.builder.setHolderId(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
