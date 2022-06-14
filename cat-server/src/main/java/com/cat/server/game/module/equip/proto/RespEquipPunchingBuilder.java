package com.cat.server.game.module.equip.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.RespEquipPunching;
import com.google.protobuf.AbstractMessage;

/**
* RespEquipPunchingBuilder
* @author Jeremy
*/
public class RespEquipPunchingBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespEquipPunchingBuilder.class);
	
	private final RespEquipPunching.Builder builder = RespEquipPunching.newBuilder();
	
	public RespEquipPunchingBuilder() {}
	
	public static RespEquipPunchingBuilder newInstance() {
		return new RespEquipPunchingBuilder();
	}
	
	public RespEquipPunching build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	/** 打孔结果,false失败,true成功**/
	public void setResult(boolean value){
		this.builder.setResult(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespEquipPunching_VALUE;
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
