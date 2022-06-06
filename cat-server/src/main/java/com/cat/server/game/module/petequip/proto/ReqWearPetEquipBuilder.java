package com.cat.server.game.module.petequip.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBPetEquip.*;
import java.util.Collection;

/**
* ReqWearPetEquipBuilder
* @author Jeremy
*/
public class ReqWearPetEquipBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqWearPetEquipBuilder.class);
	
	private final ReqWearPetEquip.Builder builder = ReqWearPetEquip.newBuilder();
	
	public ReqWearPetEquipBuilder() {}
	
	public static ReqWearPetEquipBuilder newInstance() {
		return new ReqWearPetEquipBuilder();
	}
	
	public ReqWearPetEquip build() {
		return builder.build();
	}
	
	/** 持有者id,英雄/宠物**/
	public void setHolderId(long value){
		this.builder.setHolderId(value);
	}
	/** 装备id**/
	public void setEquipId(long value){
		this.builder.setEquipId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqWearPetEquip_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
