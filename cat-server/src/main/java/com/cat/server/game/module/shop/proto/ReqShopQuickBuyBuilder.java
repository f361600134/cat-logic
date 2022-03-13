package com.cat.server.game.module.shop.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBShop.*;
import java.util.Collection;

/**
* ReqShopQuickBuyBuilder
* @author Jeremy
*/
public class ReqShopQuickBuyBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqShopQuickBuyBuilder.class);
	
	private final ReqShopQuickBuy.Builder builder = ReqShopQuickBuy.newBuilder();
	
	public ReqShopQuickBuyBuilder() {}
	
	public static ReqShopQuickBuyBuilder newInstance() {
		return new ReqShopQuickBuyBuilder();
	}
	
	public ReqShopQuickBuy build() {
		return builder.build();
	}
	
	/** 商店id**/
	public void setShopId(int value){
		this.builder.setShopId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqShopQuickBuy_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
