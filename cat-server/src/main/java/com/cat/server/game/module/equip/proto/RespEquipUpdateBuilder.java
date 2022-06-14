package com.cat.server.game.module.equip.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.PBEquipDto;
import com.cat.server.game.data.proto.PBEquip.RespEquipUpdate;
import com.google.protobuf.AbstractMessage;

/**
* RespEquipUpdateBuilder
* @author Jeremy
*/
public class RespEquipUpdateBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespEquipUpdateBuilder.class);
	
	private final RespEquipUpdate.Builder builder = RespEquipUpdate.newBuilder();
	
	public RespEquipUpdateBuilder() {}
	
	public static RespEquipUpdateBuilder newInstance() {
		return new RespEquipUpdateBuilder();
	}
	
	public RespEquipUpdate build() {
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
		return PBProtocol.RespEquipUpdate_VALUE;
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
