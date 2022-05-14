package com.cat.server.game.module.equip.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.*;
import java.util.Collection;

/**
* RespEquipInfoBuilder
* @author Jeremy
*/
public class RespEquipInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespEquipInfoBuilder.class);
	
	private final RespEquipInfo.Builder builder = RespEquipInfo.newBuilder();
	
	public RespEquipInfoBuilder() {}
	
	public static RespEquipInfoBuilder newInstance() {
		return new RespEquipInfoBuilder();
	}
	
	public RespEquipInfo build() {
		return builder.build();
	}
	
	/** 装备列表**/
	public void addEquips(PBEquipDto value){
		this.builder.addEquips(value);
	}
	
	public void addAllEquips(Collection<PBEquipDto> value){
		this.builder.addAllEquips(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespEquipInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
