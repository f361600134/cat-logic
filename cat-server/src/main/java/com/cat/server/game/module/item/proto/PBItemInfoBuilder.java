package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBCommon.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* PBItemInfoBuilder
* @author Jeremy
*/
public class PBItemInfoBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBItemInfoBuilder.class);
	
	private final PBItemInfo.Builder builder = PBItemInfo.newBuilder();
	
	public PBItemInfoBuilder() {
	}
	
	public static PBItemInfoBuilder newInstance() {
		return new PBItemInfoBuilder();
	}
	
	public PBItemInfo build() {
		return builder.build();
	}
	
	/** 唯一id**/
	public void setId(long value){
		this.builder.setId(value);
	}
	/** 道具配置id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** 数量**/
	public void setCount(int value){
		this.builder.setCount(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
