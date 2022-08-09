package com.cat.server.game.module.function.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBFunction.PBFunctionReddotDto;
import com.cat.server.game.data.proto.PBFunction.RespFunctionReddotInfo;
import com.google.protobuf.AbstractMessage;

/**
* RespFunctionReddotInfoBuilder
* @author Jeremy
*/
public class RespFunctionReddotInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespFunctionReddotInfoBuilder.class);
	
	private final RespFunctionReddotInfo.Builder builder = RespFunctionReddotInfo.newBuilder();
	
	public RespFunctionReddotInfoBuilder() {}
	
	public static RespFunctionReddotInfoBuilder newInstance() {
		return new RespFunctionReddotInfoBuilder();
	}
	
	public RespFunctionReddotInfo build() {
		return builder.build();
	}
	
	/** 功能红点信息**/
	public void addFunctionReddots(PBFunctionReddotDto value){
		this.builder.addFunctionReddots(value);
	}
	
	public void addAllFunctionReddots(Collection<PBFunctionReddotDto> value){
		this.builder.addAllFunctionReddots(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespFunctionReddotInfo_VALUE;
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
