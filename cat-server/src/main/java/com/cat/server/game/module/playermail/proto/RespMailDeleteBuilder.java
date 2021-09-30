package com.cat.server.game.module.playermail.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMail.RespMailDelete;

/**
* RespMailDeleteBuilder
* @author Jeremy
*/
public class RespMailDeleteBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespMailDeleteBuilder.class);
	
	private final RespMailDelete.Builder builder = RespMailDelete.newBuilder();
	
	public RespMailDeleteBuilder() {}
	
	public static RespMailDeleteBuilder newInstance() {
		return new RespMailDeleteBuilder();
	}
	
	public RespMailDelete build() {
		return builder.build();
	}
	
	/** 错误码**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** mail ids**/
	public void addMailIds(long value){
		this.builder.addMailIds(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespMailDelete_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
