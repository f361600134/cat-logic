package com.cat.server.game.module.chat.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBChat.AckChat;
import com.cat.server.game.data.proto.PBChat.PBChatInfo;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckChatResp
* @author Jeremy
*/
public class AckChatResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckChatResp.class);
	
	private final AckChat.Builder builder = AckChat.newBuilder();
	
	public AckChatResp() {}
	
	public static AckChatResp newInstance() {
		return new AckChatResp();
	}
	
	public AckChat build() {
		return builder.build();
	}
	
	/** 频道： 0=世界, 1=系统；2=家族；3=好友；4=队伍**/
	public void setChannel(int value){
		this.builder.setChannel(value);
	}
	/** **/
	public void addChats(PBChatInfo value){
		this.builder.addChats(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckChat_VALUE;
	}

//	@Override
//	public Builder<?, ?> getBuilder() {
//		return builder;
//	}
	
	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
