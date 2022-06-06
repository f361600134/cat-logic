package com.cat.server.game.module.petequip.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBPetEquip.*;
import java.util.Collection;

/**
* ReqPetEquipInfoBuilder
* @author Jeremy
*/
public class ReqPetEquipInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqPetEquipInfoBuilder.class);
	
	private final ReqPetEquipInfo.Builder builder = ReqPetEquipInfo.newBuilder();
	
	public ReqPetEquipInfoBuilder() {}
	
	public static ReqPetEquipInfoBuilder newInstance() {
		return new ReqPetEquipInfoBuilder();
	}
	
	public ReqPetEquipInfo build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPetEquipInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
