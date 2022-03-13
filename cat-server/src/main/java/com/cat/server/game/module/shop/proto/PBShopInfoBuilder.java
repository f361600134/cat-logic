package com.cat.server.game.module.shop.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.server.game.data.proto.PBItem.PBPairInfo;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBShop.*;
import java.util.Collection;

/**
* PBShopInfoBuilder
* @author Jeremy
*/
public class PBShopInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBShopInfoBuilder.class);
	
	private final PBShopInfo.Builder builder = PBShopInfo.newBuilder();
	
	public PBShopInfoBuilder() {}
	
	public static PBShopInfoBuilder newInstance() {
		return new PBShopInfoBuilder();
	}
	
	public PBShopInfo build() {
		return builder.build();
	}
	
	/** 商店id**/
	public void setShopId(int value){
		this.builder.setShopId(value);
	}
	/** 最后刷新时间**/
	public void setRefreshTime(long value){
		this.builder.setRefreshTime(value);
	}
	/** 刷新次数**/
	public void setRefreshNum(int value){
		this.builder.setRefreshNum(value);
	}
	/** 商品购买记录**/
	public void addItemRecord(PBPairInfo value){
		this.builder.addItemRecord(value);
	}
	
	public void addAllItemRecord(Collection<PBPairInfo> value){
		this.builder.addAllItemRecord(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
