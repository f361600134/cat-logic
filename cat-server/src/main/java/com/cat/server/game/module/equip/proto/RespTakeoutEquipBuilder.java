package com.cat.server.game.module.equip.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.RespTakeoutEquip;
import com.google.protobuf.AbstractMessage;

/**
* RespTakeoutEquipBuilder
* @author Jeremy
*/
public class RespTakeoutEquipBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespTakeoutEquipBuilder.class);
	
	private final RespTakeoutEquip.Builder builder = RespTakeoutEquip.newBuilder();
	
	public RespTakeoutEquipBuilder() {}
	
	public static RespTakeoutEquipBuilder newInstance() {
		return new RespTakeoutEquipBuilder();
	}
	
	public RespTakeoutEquip build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespTakeoutEquip_VALUE;
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
