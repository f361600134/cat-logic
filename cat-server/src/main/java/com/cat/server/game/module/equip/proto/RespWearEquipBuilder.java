package com.cat.server.game.module.equip.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.RespWearEquip;
import com.google.protobuf.AbstractMessage;

/**
* RespWearEquipBuilder
* @author Jeremy
*/
public class RespWearEquipBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespWearEquipBuilder.class);
	
	private final RespWearEquip.Builder builder = RespWearEquip.newBuilder();
	
	public RespWearEquipBuilder() {}
	
	public static RespWearEquipBuilder newInstance() {
		return new RespWearEquipBuilder();
	}
	
	public RespWearEquip build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespWearEquip_VALUE;
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
