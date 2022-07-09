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
* PBStallShelfDtoBuilder
* @author Jeremy
*/
public class PBStallShelfDtoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBStallShelfDtoBuilder.class);
	
	private final PBStallShelfDto.Builder builder = PBStallShelfDto.newBuilder();
	
	public PBStallShelfDtoBuilder() {}
	
	public static PBStallShelfDtoBuilder newInstance() {
		return new PBStallShelfDtoBuilder();
	}
	
	public PBStallShelfDto build() {
		return builder.build();
	}
	
	/** 商品配置id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** 商品唯一id**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
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
