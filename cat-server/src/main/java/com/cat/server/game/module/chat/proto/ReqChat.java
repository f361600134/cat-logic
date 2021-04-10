package com.cat.server.game.module.chat.proto;

import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBPlayer;

import com.google.protobuf.AbstractMessageLite.Builder;

public class ReqChat implements IProtocol{
	
	private PBPlayer.ReqChat.Builder builder;
	
	public ReqChat() {
		this.builder = PBPlayer.ReqChat.newBuilder();
		this.set();
	}
	
	public void set() {
		builder.setChatChannel(1);
		builder.setContent("你好, 大傻逼");
		builder.setPlayerId(-1);
	}
	
	
	public static ReqChat create() {
		return new ReqChat();
	}
	
	@Override
	public short protocol() {
		return PBProtocol.ReqChat_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}

}
