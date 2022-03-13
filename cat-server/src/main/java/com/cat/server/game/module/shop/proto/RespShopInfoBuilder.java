package com.cat.server.game.module.shop.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBShop.*;
import java.util.Collection;

/**
* RespShopInfoBuilder
* @author Jeremy
*/
public class RespShopInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespShopInfoBuilder.class);
	
	private final RespShopInfo.Builder builder = RespShopInfo.newBuilder();
	
	public RespShopInfoBuilder() {}
	
	public static RespShopInfoBuilder newInstance() {
		return new RespShopInfoBuilder();
	}
	
	public RespShopInfo build() {
		return builder.build();
	}
	
	/** 错误码为0时读取,指定商店信息**/
	public void addShopInfos(PBShopInfo value){
		this.builder.addShopInfos(value);
	}
	
	public void addAllShopInfos(Collection<PBShopInfo> value){
		this.builder.addAllShopInfos(value);
	}
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespShopInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
