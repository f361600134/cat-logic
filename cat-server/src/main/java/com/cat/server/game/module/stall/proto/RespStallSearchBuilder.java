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
* RespStallSearchBuilder
* @author Jeremy
*/
public class RespStallSearchBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespStallSearchBuilder.class);
	
	private final RespStallSearch.Builder builder = RespStallSearch.newBuilder();
	
	public RespStallSearchBuilder() {}
	
	public static RespStallSearchBuilder newInstance() {
		return new RespStallSearchBuilder();
	}
	
	public RespStallSearch build() {
		return builder.build();
	}
	
	/** 摊位信息**/
	public void addStallDtos(PBStallDto value){
		this.builder.addStallDtos(value);
	}
	
	public void addAllStallDtos(Collection<PBStallDto> value){
		this.builder.addAllStallDtos(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespStallSearch_VALUE;
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
