package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBItem.*;

/**
* RespItemUseBuilder
* @author Jeremy
*/
public class RespItemUseBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespItemUseBuilder.class);
	
	private final RespItemUse.Builder builder = RespItemUse.newBuilder();
	
	public RespItemUseBuilder() {}
	
	public static RespItemUseBuilder newInstance() {
		return new RespItemUseBuilder();
	}
	
	public RespItemUse build() {
		return builder.build();
	}
	
	/** **/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 奖励**/
	public void addRewards(PBRewardInfo value){
		this.builder.addRewards(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespItemUse_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
