package com.cat.server.game.module.shop.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBShop.*;
import java.util.Collection;

/**
* RespShopBuyBuilder
* @author Jeremy
*/
public class RespShopBuyBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespShopBuyBuilder.class);
	
	private final RespShopBuy.Builder builder = RespShopBuy.newBuilder();
	
	public RespShopBuyBuilder() {}
	
	public static RespShopBuyBuilder newInstance() {
		return new RespShopBuyBuilder();
	}
	
	public RespShopBuy build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespShopBuy_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
