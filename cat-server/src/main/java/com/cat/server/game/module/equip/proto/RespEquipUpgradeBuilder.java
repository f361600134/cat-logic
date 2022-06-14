package com.cat.server.game.module.equip.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.RespEquipUpgrade;
import com.google.protobuf.AbstractMessage;

/**
* RespEquipUpgradeBuilder
* @author Jeremy
*/
public class RespEquipUpgradeBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespEquipUpgradeBuilder.class);
	
	private final RespEquipUpgrade.Builder builder = RespEquipUpgrade.newBuilder();
	
	public RespEquipUpgradeBuilder() {}
	
	public static RespEquipUpgradeBuilder newInstance() {
		return new RespEquipUpgradeBuilder();
	}
	
	public RespEquipUpgrade build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	/** 加工结果,false失败,true成功**/
	public void setResult(boolean value){
		this.builder.setResult(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespEquipUpgrade_VALUE;
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
