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
* PBStallDtoBuilder
* @author Jeremy
*/
public class PBStallDtoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBStallDtoBuilder.class);
	
	private final PBStallDto.Builder builder = PBStallDto.newBuilder();
	
	public PBStallDtoBuilder() {}
	
	public static PBStallDtoBuilder newInstance() {
		return new PBStallDtoBuilder();
	}
	
	public PBStallDto build() {
		return builder.build();
	}
	
	/** 摆摊人id**/
	public void setPlayerId(long value){
		this.builder.setPlayerId(value);
	}
	/** 摊位名字**/
	public void setName(String value){
		this.builder.setName(value);
	}
	/** 商品ids**/
	public void addCommodityIds(long value){
		this.builder.addCommodityIds(value);
	}
	
	public void addAllCommodityIds(Collection<java.lang.Long> value){
		this.builder.addAllCommodityIds(value);
	}
	/** 摆摊商品列表信息**/
	public void addCommodities(PBStallCommodityInfo value){
		this.builder.addCommodities(value);
	}
	
	public void addAllCommodities(Collection<PBStallCommodityInfo> value){
		this.builder.addAllCommodities(value);
	}
	
	@Override
	public int protocol() {
		return 0;
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
