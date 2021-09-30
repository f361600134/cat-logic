package com.cat.server.game.module.chat.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBChat.*;

/**
* RespChatBuilder
* @author Jeremy
*/
public class RespChatBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespChatBuilder.class);
	
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
	/** **/
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
