package com.cat.server.game.module.stall.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBEquip.PBEquipDto;
import com.cat.server.game.data.proto.PBItem.PBItemInfo;
import com.cat.server.game.data.proto.PBPet.PBPetDto;
import com.cat.server.game.data.proto.PBPetEquip.PBPetEquipDto;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBStall.PBStallCommodityInfo;
import com.google.protobuf.AbstractMessage;

/**
* PBStallCommodityInfoBuilder
* @author Jeremy
*/
public class PBStallCommodityInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBStallCommodityInfoBuilder.class);
	
	private final PBStallCommodityInfo.Builder builder = PBStallCommodityInfo.newBuilder();
	
	public PBStallCommodityInfoBuilder() {}
	
	public static PBStallCommodityInfoBuilder newInstance() {
		return new PBStallCommodityInfoBuilder();
	}
	
	public PBStallCommodityInfo build() {
		return builder.build();
	}
	
	/** 装备列表**/
	public void addEquips(PBEquipDto value){
		this.builder.addEquips(value);
	}
	
	public void addAllEquips(Collection<PBEquipDto> value){
		this.builder.addAllEquips(value);
	}
	/** 宠物装备列表**/
	public void addPetEquips(PBPetEquipDto value){
		this.builder.addPetEquips(value);
	}
	
	public void addAllPetEquips(Collection<PBPetEquipDto> value){
		this.builder.addAllPetEquips(value);
	}
	/** 宠物列表**/
	public void addPets(PBPetDto value){
		this.builder.addPets(value);
	}
	
	public void addAllPets(Collection<PBPetDto> value){
		this.builder.addAllPets(value);
	}
	/** 道具列表**/
	public void addItems(PBItemInfo value){
		this.builder.addItems(value);
	}
	
	public void addAllItems(Collection<PBItemInfo> value){
		this.builder.addAllItems(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return builder.build();
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
