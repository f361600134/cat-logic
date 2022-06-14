package com.cat.server.game.module.equip.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.PBEquipDto;
import com.cat.server.game.data.proto.PBEquip.RespEquipInfo;
import com.google.protobuf.AbstractMessage;

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
	public AbstractMessage getBuilder() {
		return builder.build();
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
