package com.cat.server.game.module.equip.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.*;
import java.util.Collection;

/**
* ReqEquipInfoBuilder
* @author Jeremy
*/
public class ReqEquipInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqEquipInfoBuilder.class);
	
	private final ReqEquipInfo.Builder builder = ReqEquipInfo.newBuilder();
	
	public ReqEquipInfoBuilder() {}
	
	public static ReqEquipInfoBuilder newInstance() {
		return new ReqEquipInfoBuilder();
	}
	
	public ReqEquipInfo build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.ReqEquipInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
