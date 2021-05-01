package com.cat.server.game.module.chat.type;

import java.math.BigInteger;
import java.util.Collection;

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
	 * @param sender   发送者玩家对象
	 * @param targetId 发送目标id
	 * @return
	 */
	public BigInteger getUniqueId(final long senderId, final long targetId);
	
	/**
	 * 获取接收者id列表
	 * 
	 * @param sender
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
	 * @return
	 */
	public Pair<Long, Long> getOriginalIds(BigInteger uniqueId);
	
	/**
	 * 聊天校验, 聊天内容不在此做校验, 此处仅仅对不同渠道下, 做一些渠道内的校验
	 * @param senderId
	 * @return
	 */
	public ErrorCode checkChat(long senderId, BigInteger uniqueId);
	
	/**
	 * 发送消息
	 * @return
	 */
	public ErrorCode sendMsg(ChatDetail chatDetails);
	
//	/**
//	 * 获取所有聊天记录
//	 * @param sender
//	 * @param targetId
//	 * @return
//	 */
//	public List<ChatDetail> getAllChatRecord(Player sender, long targetId);
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
