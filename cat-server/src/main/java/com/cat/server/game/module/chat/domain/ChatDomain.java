package com.cat.server.game.module.chat.domain;


import java.math.BigInteger;
import java.util.Collection;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.type.IChatType;
import com.cat.server.utils.Pair;

/**
 * 聊天域
 * 队伍聊天, 好友聊天不做持久化,
 * 世界聊天, 系统聊天, 家族聊天做少量持久化 
 * @author Jeremy
 */
public class ChatDomain implements IChatType{
	
	/**
	 * 频道id
	 */
	private int id;
	
	private IChatType chatType;
	
	public ChatDomain(int id) {
		this.id = id;
		chatType = ChannelType.channelTypeMap.get(id);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IChatType getChatType() {
		return chatType;
	}

	public void setChatType(IChatType chatType) {
		this.chatType = chatType;
	}
	
	@Override
	public int getChannel() {
		return chatType.getChannel();
	}

	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
		return chatType.getUniqueId(senderId, targetId);
	}

	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
		return chatType.findReceiverIds(uniqueId);
	}

	@Override
	public Pair<Long, Long> getOriginalIds(BigInteger uniqueId) {
		return chatType.getOriginalIds(uniqueId);
	}

	@Override
	public ErrorCode checkChat(long senderId, BigInteger uniqueId) {
		return chatType.checkChat(senderId, uniqueId);
	}

	@Override
	public ErrorCode sendMsg(ChatDetail chatDetails) {
		return chatType.sendMsg(chatDetails);
	}
	
}
