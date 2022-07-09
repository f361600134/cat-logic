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
* ReqStartStallBuilder
* @author Jeremy
*/
public class ReqStartStallBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqStartStallBuilder.class);
	
	private final ReqStartStall.Builder builder = ReqStartStall.newBuilder();
	
	public ReqStartStallBuilder() {}
	
	public static ReqStartStallBuilder newInstance() {
		return new ReqStartStallBuilder();
	}
	
	public ReqStartStall build() {
		return builder.build();
	}
	
	/** 店铺名字**/
	public void setName(String value){
		this.builder.setName(value);
	}
	/** 商品列表**/
	public void addCommodities(PBStallShelfDto value){
		this.builder.addCommodities(value);
	}
	
	public void addAllCommodities(Collection<PBStallShelfDto> value){
		this.builder.addAllCommodities(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqStartStall_VALUE;
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
