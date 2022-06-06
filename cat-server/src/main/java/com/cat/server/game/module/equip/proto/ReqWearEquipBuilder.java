package com.cat.server.game.module.equip.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.*;
import java.util.Collection;

/**
* ReqWearEquipBuilder
* @author Jeremy
*/
public class ReqWearEquipBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqWearEquipBuilder.class);
	
	private final ReqWearEquip.Builder builder = ReqWearEquip.newBuilder();
	
	public ReqWearEquipBuilder() {}
	
	public static ReqWearEquipBuilder newInstance() {
		return new ReqWearEquipBuilder();
	}
	
	public ReqWearEquip build() {
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
		return PBProtocol.ReqWearEquip_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
