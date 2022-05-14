package com.cat.server.game.module.equip.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.*;
import java.util.Collection;

/**
* PBEquipDtoBuilder
* @author Jeremy
*/
public class PBEquipDtoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBEquipDtoBuilder.class);
	
	private final PBEquipDto.Builder builder = PBEquipDto.newBuilder();
	
	public PBEquipDtoBuilder() {}
	
	public static PBEquipDtoBuilder newInstance() {
		return new PBEquipDtoBuilder();
	}
	
	public PBEquipDto build() {
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
