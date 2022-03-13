package com.cat.server.game.module.shop.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBShop.*;
import java.util.Collection;

/**
* ReqShopInfoBuilder
* @author Jeremy
*/
public class ReqShopInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqShopInfoBuilder.class);
	
	private final ReqShopInfo.Builder builder = ReqShopInfo.newBuilder();
	
	public ReqShopInfoBuilder() {}
	
	public static ReqShopInfoBuilder newInstance() {
		return new ReqShopInfoBuilder();
	}
	
	public ReqShopInfo build() {
		return builder.build();
	}
	
	/** 商店id**/
	public void addShopId(int value){
		this.builder.addShopId(value);
	}
	
	public void addAllShopId(Collection<java.lang.Integer> value){
		this.builder.addAllShopId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqShopInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
