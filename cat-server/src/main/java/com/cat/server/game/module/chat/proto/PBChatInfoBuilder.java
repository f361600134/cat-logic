package com.cat.server.game.module.chat.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBChat.PBChatInfo;
import com.cat.server.game.data.proto.PBPlayer.PBPlayerProfile;

/**
* PBChatInfoBuilder
* @author Jeremy
*/
public class PBChatInfoBuilder extends AbstractProtocol {

	private final PBChatInfo.Builder builder = PBChatInfo.newBuilder();
	
	public PBChatInfoBuilder() {}
	
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
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
