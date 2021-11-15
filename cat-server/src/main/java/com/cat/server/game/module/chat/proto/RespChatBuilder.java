package com.cat.server.game.module.chat.proto;

import com.cat.net.network.base.AbstractProtocol;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBChat.PBChatInfo;
import com.cat.server.game.data.proto.PBChat.RespChat;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;

/**
* RespChatBuilder
* @author Jeremy
*/
public class RespChatBuilder extends AbstractProtocol {

	private final RespChat.Builder builder = RespChat.newBuilder();
	
	public RespChatBuilder() {}
	
	public static RespChatBuilder newInstance() {
		return new RespChatBuilder();
	}
	
	public RespChat build() {
		return builder.build();
	}
	
	/** 频道： 0=世界, 1=系统；2=家族；3=好友；4=队伍**/
	public void setChannel(int value){
		this.builder.setChannel(value);
	}
	/** 聊天内容**/
	public void addChats(PBChatInfo value){
		this.builder.addChats(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespChat_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
