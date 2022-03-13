package com.cat.server.game.module.shop.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBShop.*;
import java.util.Collection;

/**
* RespShopQuickBuyBuilder
* @author Jeremy
*/
public class RespShopQuickBuyBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespShopQuickBuyBuilder.class);
	
	private final RespShopQuickBuy.Builder builder = RespShopQuickBuy.newBuilder();
	
	public RespShopQuickBuyBuilder() {}
	
	public static RespShopQuickBuyBuilder newInstance() {
		return new RespShopQuickBuyBuilder();
	}
	
	public RespShopQuickBuy build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespShopQuickBuy_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
