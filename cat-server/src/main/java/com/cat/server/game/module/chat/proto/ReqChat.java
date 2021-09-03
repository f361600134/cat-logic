package com.cat.server.game.module.chat.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBChat;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;

public class ReqChat extends AbstractProtocol{
	
	private PBChat.ReqChat.Builder builder;
	
	public ReqChat() {
		this.builder = PBChat.ReqChat.newBuilder();
		this.set();
	}
	
	public void set() {
		builder.setChannel(1);
		builder.setContent("你好, 大傻逼");
		builder.setRecvId(-1);
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
