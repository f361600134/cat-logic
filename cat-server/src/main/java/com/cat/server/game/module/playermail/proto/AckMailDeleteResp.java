package com.cat.server.game.module.playermail.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBMail.AckMailDelete;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckMailDeleteResp
* @author Jeremy
*/
public class AckMailDeleteResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckMailDeleteResp.class);
	
	private final AckMailDelete.Builder builder = AckMailDelete.newBuilder();
	
	public AckMailDeleteResp() {}
	
	public static AckMailDeleteResp newInstance() {
		return new AckMailDeleteResp();
	}
	
	public AckMailDelete build() {
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
		return PBProtocol.AckMailDelete_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
