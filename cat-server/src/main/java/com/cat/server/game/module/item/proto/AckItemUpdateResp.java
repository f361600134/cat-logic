package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBCommon.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* AckItemUpdateResp
* @author Jeremy
*/
public class AckItemUpdateResp implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckItemUpdateResp.class);
	
	private final AckItemUpdate.Builder builder = AckItemUpdate.newBuilder();
	
	public AckItemUpdateResp() {}
	
	public static AckItemUpdateResp newInstance() {
		return new AckItemUpdateResp();
	}
	
	public AckItemUpdate build() {
		return builder.build();
	}
	
	/** 道具列表**/
	public void addItems(PBItemInfo value){
		this.builder.addItems(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckItemUpdate_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
