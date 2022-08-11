package com.cat.server.game.module.function.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBFunction.*;
import java.util.Collection;

/**
* RespFunctionSwitchNoticeBuilder
* @author Jeremy
*/
public class RespFunctionSwitchNoticeBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespFunctionSwitchNoticeBuilder.class);
	
	private final RespFunctionSwitchNotice.Builder builder = RespFunctionSwitchNotice.newBuilder();
	
	public RespFunctionSwitchNoticeBuilder() {}
	
	public static RespFunctionSwitchNoticeBuilder newInstance() {
		return new RespFunctionSwitchNoticeBuilder();
	}
	
	public RespFunctionSwitchNotice build() {
		return builder.build();
	}
	
	/** 强行关闭功能列表**/
	public void addForceCloseIds(int value){
		this.builder.addForceCloseIds(value);
	}
	
	public void addAllForceCloseIds(Collection<java.lang.Integer> value){
		this.builder.addAllForceCloseIds(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespFunctionSwitchNotice_VALUE;
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
