package com.cat.server.game.module.chat.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.PBPlayer.*;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBChat.*;

/**
* PBChatInfoBuilder
* @author Jeremy
*/
public class PBChatInfoBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBChatInfoBuilder.class);
	
	private final PBChatInfo.Builder builder = PBChatInfo.newBuilder();
	
	public PBChatInfoBuilder() {
	}
	
	public static PBChatInfoBuilder newInstance() {
		return new PBChatInfoBuilder();
	}
	
	public PBChatInfo build() {
		return builder.build();
	}
	
	/** 发送者简要信息**/
	public void setProfile(PBPlayerProfile value){
		this.builder.setProfile(value);
	}
	/** 聊天内容**/
	public void setContent(String value){
		this.builder.setContent(value);
	}
	/** 发送时间**/
	public void setSendTime(long value){
		this.builder.setSendTime(value);
	}
	/** 接收者简要信息,私聊使用**/
	public void setRecvProfile(PBPlayerProfile value){
		this.builder.setRecvProfile(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
