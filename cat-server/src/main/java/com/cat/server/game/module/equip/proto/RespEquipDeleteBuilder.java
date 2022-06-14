package com.cat.server.game.module.equip.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.RespEquipDelete;
import com.google.protobuf.AbstractMessage;

/**
* RespEquipDeleteBuilder
* @author Jeremy
*/
public class RespEquipDeleteBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespEquipDeleteBuilder.class);
	
	private final RespEquipDelete.Builder builder = RespEquipDelete.newBuilder();
	
	public RespEquipDeleteBuilder() {}
	
	public static RespEquipDeleteBuilder newInstance() {
		return new RespEquipDeleteBuilder();
	}
	
	public RespEquipDelete build() {
		return builder.build();
	}
	
	/** 装备id列表**/
	public void addEquipIds(long value){
		this.builder.addEquipIds(value);
	}
	
	public void addAllEquipIds(Collection<java.lang.Long> value){
		this.builder.addAllEquipIds(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespEquipDelete_VALUE;
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
