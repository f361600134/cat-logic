package com.cat.server.game.module.shop.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBShop.*;
import java.util.Collection;

/**
* ReqShopBuyBuilder
* @author Jeremy
*/
public class ReqShopBuyBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqShopBuyBuilder.class);
	
	private final ReqShopBuy.Builder builder = ReqShopBuy.newBuilder();
	
	public ReqShopBuyBuilder() {}
	
	public static ReqShopBuyBuilder newInstance() {
		return new ReqShopBuyBuilder();
	}
	
	public ReqShopBuy build() {
		return builder.build();
	}
	
	/** 商店id**/
	public void setShopId(int value){
		this.builder.setShopId(value);
	}
	/** 商品id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** 商品数量**/
	public void setNumber(int value){
		this.builder.setNumber(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqShopBuy_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
