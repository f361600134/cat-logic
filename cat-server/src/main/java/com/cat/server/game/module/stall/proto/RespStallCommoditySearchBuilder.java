package com.cat.server.game.module.stall.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBStall.*;
import java.util.Collection;

/**
* RespStallCommoditySearchBuilder
* @author Jeremy
*/
public class RespStallCommoditySearchBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespStallCommoditySearchBuilder.class);
	
	private final RespStallCommoditySearch.Builder builder = RespStallCommoditySearch.newBuilder();
	
	public RespStallCommoditySearchBuilder() {}
	
	public static RespStallCommoditySearchBuilder newInstance() {
		return new RespStallCommoditySearchBuilder();
	}
	
	public RespStallCommoditySearch build() {
		return builder.build();
	}
	
	/** 商品列表**/
	public void setCommodityInfo(PBStallCommodityInfo value){
		this.builder.setCommodityInfo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespStallCommoditySearch_VALUE;
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return builder.build();
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
