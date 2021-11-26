package com.cat.server.game.module.mail.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMail.*;

/**
* RespMailReadBuilder
* @author Jeremy
*/
public class RespMailReadBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespMailReadBuilder.class);
	
	private final RespMailRead.Builder builder = RespMailRead.newBuilder();
	
	public RespMailReadBuilder() {}
	
	public static RespMailReadBuilder newInstance() {
		return new RespMailReadBuilder();
	}
	
	public RespMailRead build() {
		return builder.build();
	}
	
	/** 错误码,非0表示弹提示**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespMailRead_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
