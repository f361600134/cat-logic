package com.cat.server.game.module.chat.type;

import java.math.BigInteger;
import java.util.Collection;

import com.cat.server.game.data.proto.PBChat;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.domain.ChatDetail;
import com.cat.server.utils.Pair;

public interface IChatType {
	
	/**
	 * 获取频道
	 * @return
	 */
	public int getChannel();

	/**
	 * 通过玩家信息,目标id生成唯一的id, 全频道通用, 每个
	 * 
	 * @param senderId   发送者玩家id
	 * @param targetId 发送目标id
	 * @return
	 */
	public BigInteger getUniqueId(final long senderId, final long targetId);
	
	/**
	 * 获取接收者id列表
	 * @param uniqueId 通过uniqueId获取到接收者id列表
	 * @return
	 */
	public Collection<Long> findReceiverIds(BigInteger uniqueId);
	
//	/**
//	 * 删除聊天
//	 */
//	default public ErrorCode deleteChat(long playerId, long targetId) {
//		//	默认不实现, 有需要的则实现.
//		return ErrorCode.CHAT_CHANNEL_NOT_ALLOWED_DELETE;
//	}
	
	/**
	 * 获取原始的id
	 * @param uniqueId 唯一id, 通过此id解析成原始得两个id
	 * @return 原始id由两个值组成, 所以这里返回的是一个pair对象
	 */
	public Pair<Long, Long> getOriginalIds(BigInteger uniqueId);
	
	/**
	 * 聊天校验, 聊天内容不在此做校验, 此处仅仅对不同渠道下, 做一些渠道内的校验
	 * @param senderId 发送者id
	 * @param uniqueId 唯一id
	 * @return 错误码
	 */
	public ErrorCode checkChat(long senderId, BigInteger uniqueId);
	
	/**
	 * 发送消息
	 * @return
	 */
	public ErrorCode sendMsg(ChatDetail chatDetails);
	
	/**
	 * 获取所有聊天记录
	 * @param playerId 请求聊天记录的玩家id
	 * @param targetId 目标id,好友聊天才有具体含义
	 * @return 聊天记录
	 */
	public Collection<ChatDetail> getAllChatRecord(long playerId, long targetId);

	/**
	 * 构建聊天协议对象
	 * @param chatDetail 聊天内容
	 * @return 聊天内容dto
	 */
	public PBChat.PBChatInfo buildChatDto(ChatDetail chatDetail);
//	
//	/**
//	 * 获取最新的一条聊天记录
//	 * @param sender
//	 * @param targetId
//	 * @return
//	 */
//	public ChatDetail getLastChatRecord(Player sender, long targetId);
//	
//	
//	/**
//	 * 获取指定数量的聊天记录
//	 * @param sender
//	 * @param targetId
//	 * @return
//	 */
//	public List<ChatDetail> getChatRecord(Player sender, long targetId, int num);
	
}
