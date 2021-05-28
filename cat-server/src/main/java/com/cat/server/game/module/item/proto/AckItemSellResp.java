package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.PBCommon.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* AckItemSellResp
* @author Jeremy
*/
public class AckItemSellResp implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckItemSellResp.class);
	
	private final AckItemSell.Builder builder = AckItemSell.newBuilder();
	
	public AckItemSellResp() {}
	
	public static AckItemSellResp newInstance() {
		return new AckItemSellResp();
	}
	
	public AckItemSell build() {
		return builder.build();
	}
	
	/** **/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckItemSell_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
