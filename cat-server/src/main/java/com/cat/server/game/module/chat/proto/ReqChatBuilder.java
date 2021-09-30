package com.cat.server.game.module.chat.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBChat.*;

/**
* ReqChatBuilder
* @author Jeremy
*/
public class ReqChatBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqChatBuilder.class);
	
	private final ReqChat.Builder builder = ReqChat.newBuilder();
	
	public ReqChatBuilder() {}
	
	public static ReqChatBuilder newInstance() {
		return new ReqChatBuilder();
	}
	
	public ReqChat build() {
		return builder.build();
	}
	
	/** 内容**/
	public void setContent(String value){
		this.builder.setContent(value);
	}
	/** 频道**/
	public void setChannel(int value){
		this.builder.setChannel(value);
	}
	/** 接收者id**/
	public void setRecvId(long value){
		this.builder.setRecvId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqChat_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
