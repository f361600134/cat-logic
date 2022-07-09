package com.cat.server.game.module.shop.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBCommon.PBPairInfo;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBShop.PBShopInfo;

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
	/** 免费刷新的最后刷新时间**/
	public void setFreeRefreshTime(long value){
		this.builder.setFreeRefreshTime(value);
	}
	/** 免费刷新已刷新次数**/
	public void setFreeRefreshNum(int value){
		this.builder.setFreeRefreshNum(value);
	}
	/** 商品购买记录**/
	public void addItemRecord(PBPairInfo value){
		this.builder.addItemRecord(value);
	}
	
	public void addAllItemRecord(Collection<PBPairInfo> value){
		this.builder.addAllItemRecord(value);
	}
	/** 资源刷新已刷新次数**/
	public void setResRefreshNum(int value){
		this.builder.setResRefreshNum(value);
	}
	/** 当前商品id列表**/
	public void addCommodities(int value){
		this.builder.addCommodities(value);
	}
	
	public void addAllCommodities(Collection<java.lang.Integer> value){
		this.builder.addAllCommodities(value);
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
