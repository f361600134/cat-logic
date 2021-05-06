package com.cat.robot.module.chat.proto;

import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBPlayer;

import com.google.protobuf.AbstractMessageLite.Builder;

public class ReqChat implements IProtocol{
	
	private PBPlayer.ReqChat.Builder builder;
	
	public ReqChat() {
		this.builder = PBPlayer.ReqChat.newBuilder();
	}
	
//	public void set() {
//		builder.setChatChannel(0);
//		builder.setContent("@resource 10001,1,10002,-10");
//		builder.setPlayerId(-1);
//	}
	
	public void setChatChannel(int value) {
		builder.setChatChannel(value);
	}
	
	public void setContent(String value) {
		builder.setContent(value);
	}
	
	public void setPlayerId(long value) {
		builder.setPlayerId(value);
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
