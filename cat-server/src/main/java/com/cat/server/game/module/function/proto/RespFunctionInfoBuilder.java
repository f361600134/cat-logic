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
* RespFunctionInfoBuilder
* @author Jeremy
*/
public class RespFunctionInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespFunctionInfoBuilder.class);
	
	private final RespFunctionInfo.Builder builder = RespFunctionInfo.newBuilder();
	
	public RespFunctionInfoBuilder() {}
	
	public static RespFunctionInfoBuilder newInstance() {
		return new RespFunctionInfoBuilder();
	}
	
	public RespFunctionInfo build() {
		return builder.build();
	}
	
	/** 功能信息**/
	public void addFunctions(PBFunctionDto value){
		this.builder.addFunctions(value);
	}
	
	public void addAllFunctions(Collection<PBFunctionDto> value){
		this.builder.addAllFunctions(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespFunctionInfo_VALUE;
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
