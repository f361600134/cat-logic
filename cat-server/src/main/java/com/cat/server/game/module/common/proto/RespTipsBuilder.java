package com.cat.server.game.module.common.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBCommon.RespTips;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;

/**
* RespTipsBuilder
* @author Jeremy
*/
public class RespTipsBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespTipsBuilder.class);
	
	private final RespTips.Builder builder = RespTips.newBuilder();
	
	public RespTipsBuilder() {}
	
	public static RespTipsBuilder newInstance() {
		return new RespTipsBuilder();
	}
	
	public RespTips build() {
		return builder.build();
	}
	
	/** 错误码id**/
	public void setTipsId(int value){
		this.builder.setTipsId(value);
	}
	/** **/
	public void setParams(int value){
		this.builder.setParams(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespTips_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
