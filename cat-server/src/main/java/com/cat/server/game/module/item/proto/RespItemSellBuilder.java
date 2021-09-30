package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* RespItemSellBuilder
* @author Jeremy
*/
public class RespItemSellBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespItemSellBuilder.class);
	
	private final RespItemSell.Builder builder = RespItemSell.newBuilder();
	
	public RespItemSellBuilder() {}
	
	public static RespItemSellBuilder newInstance() {
		return new RespItemSellBuilder();
	}
	
	public RespItemSell build() {
		return builder.build();
	}
	
	/** **/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespItemSell_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
