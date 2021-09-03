package com.cat.robot.module.chat.proto;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBChat;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;

public class ReqChat extends AbstractProtocol{
	
	private PBChat.ReqChat.Builder builder;
	
	public ReqChat() {
		this.builder = PBChat.ReqChat.newBuilder();
	}
	
//	public void set() {
//		builder.setChatChannel(0);
//		builder.setContent("@resource 10001,1,10002,-10");
//		builder.setPlayerId(-1);
//	}
	
	public void setChannel(int value) {
		builder.setChannel(value);
	}
	
	public void setContent(String value) {
		builder.setContent(value);
	}
	
	public void setRecvId(long value) {
		builder.setRecvId(value);
	}
	
	public static ReqChat create() {
		return new ReqChat();
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqChat_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}

}
