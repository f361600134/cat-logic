package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* RespPlayerRandNameBuilder
* @author Jeremy
*/
public class RespPlayerRandNameBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespPlayerRandNameBuilder.class);
	
	private final RespPlayerRandName.Builder builder = RespPlayerRandName.newBuilder();
	
	public RespPlayerRandNameBuilder() {}
	
	public static RespPlayerRandNameBuilder newInstance() {
		return new RespPlayerRandNameBuilder();
	}
	
	public RespPlayerRandName build() {
		return builder.build();
	}
	
	/** 名字列表,有5个**/
	public void addNames(String value){
		this.builder.addNames(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPlayerRandName_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
