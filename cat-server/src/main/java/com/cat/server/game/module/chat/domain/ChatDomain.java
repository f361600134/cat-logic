package com.cat.server.game.module.chat.domain;


import java.math.BigInteger;
import java.util.Collection;

import com.cat.server.game.data.proto.PBChat;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.type.IChatType;
import com.cat.server.utils.Pair;

/**
 * 聊天域
 * 队伍聊天, 不做持久化
 * 好友聊天不做持久化, 历史聊天记录保存至客户端
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

	@Override
	public Collection<ChatDetail> getAllChatRecord(long playerId, long targetId) {
		return chatType.getAllChatRecord(playerId, targetId);
	}

	@Override
	public PBChat.PBChatInfo buildChatDto(ChatDetail chatDetail) {
		return chatType.buildChatDto(chatDetail);
	}
}
