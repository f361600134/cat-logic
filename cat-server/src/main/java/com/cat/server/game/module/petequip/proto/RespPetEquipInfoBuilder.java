package com.cat.server.game.module.petequip.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBPetEquip.PBPetEquipDto;
import com.cat.server.game.data.proto.PBPetEquip.RespPetEquipInfo;

/**
* RespPetEquipInfoBuilder
* @author Jeremy
*/
public class RespPetEquipInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespPetEquipInfoBuilder.class);
	
	private final RespPetEquipInfo.Builder builder = RespPetEquipInfo.newBuilder();
	
	public RespPetEquipInfoBuilder() {}
	
	public static RespPetEquipInfoBuilder newInstance() {
		return new RespPetEquipInfoBuilder();
	}
	
	public RespPetEquipInfo build() {
		return builder.build();
	}
	
	/** 宠物装备列表**/
	public void addPetEquips(PBPetEquipDto value){
		this.builder.addPetEquips(value);
	}
	
	public void addAllPetEquips(Collection<PBPetEquipDto> value){
		this.builder.addAllPetEquips(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPetEquipInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
