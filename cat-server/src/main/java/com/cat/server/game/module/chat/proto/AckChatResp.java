package com.cat.server.game.module.chat.proto;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.AckChat;
import com.cat.server.game.data.proto.PBPlayer.ChatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessageLite.Builder;

public class AckChatResp implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckChatResp.class);
	
	private AckChat.Builder builder;

	public static AckChatResp newInstance(){
		return new AckChatResp();
	}
	
    public AckChatResp() {
        this.builder = AckChat.newBuilder();
    }
    
    public void addChat(int channel, ChatInfo chatInfo){
    	this.builder.setChatChannel(channel);
    	this.builder.addChats(chatInfo);
    }
    
    public void setChatChannel(int channel) {
    	this.builder.setChatChannel(channel);
    }
    
    public void addChats(ChatInfo chatInfo) {
    	this.builder.addChats(chatInfo);
    }
    
	@Override
	public short protocol() {
		return PBProtocol.AckChat_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
	
}
