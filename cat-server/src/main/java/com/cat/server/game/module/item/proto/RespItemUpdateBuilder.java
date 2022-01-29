package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBItem.PBItemInfo;
import com.cat.server.game.data.proto.PBItem.RespItemUpdate;

/**
* RespItemUpdateBuilder
* @author Jeremy
*/
public class RespItemUpdateBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespItemUpdateBuilder.class);
	
	private final RespItemUpdate.Builder builder = RespItemUpdate.newBuilder();
	
	public RespItemUpdateBuilder() {}
	
	public static RespItemUpdateBuilder newInstance() {
		return new RespItemUpdateBuilder();
	}
	
	public RespItemUpdate build() {
		return builder.build();
	}
	
	/** 道具列表**/
	public void addItems(PBItemInfo value){
		this.builder.addItems(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespItemUpdate_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
