package com.cat.server.game.module.chat.type;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.Chat;
import com.cat.server.game.module.chat.domain.ChatDetail;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.player.domain.PlayerContext;
import com.cat.server.utils.Pair;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;


public class FriendChat extends AbstractChatType{
	
	/**
	 * 聊天缓存, 所有频道聊天缓存, 使用Cache替代掉Map
	 * key:BigInteger, uniqueId, 唯一id
	 * value:聊天记录实体bean
	 */
	private Cache<BigInteger, Chat> chatRecordMap = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)// 在给定时间内没有被读/写访问,则清除
			.maximumSize(1000)//	最大条目,超过这个聊天记录, 根据LRU特点移除
			.build();

	public FriendChat() {
		super(ChannelType.FRIEND.getChannel());
	}

	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
		// 私聊,通过发送方的玩家id,和目标玩家id,组装成唯一id
		long tempId = targetId;
		if (senderId < tempId) {
			senderId ^= tempId;
			tempId ^= senderId;
			senderId ^= tempId;
		}
		BigInteger bigInteger = BigInteger.valueOf(senderId).shiftLeft(64).add(BigInteger.valueOf(tempId));
		return bigInteger;
	}
	
	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
		Pair<Long, Long> pair = getOriginalIds(uniqueId);
		return Lists.newArrayList(pair.getLeft(), pair.getRight());
	}
	
	@Override
	public ErrorCode checkChat(long senderId, BigInteger uniqueId) {
		ErrorCode errorCode = super.checkChat(senderId, uniqueId);
		if (!errorCode.isSuccess()) {
			return errorCode;
		}
		Pair<Long, Long> pair = getOriginalIds(uniqueId);
		long friendId = pair.getLeft() == senderId ? pair.getRight() : pair.getLeft();
		PlayerContext playerContext = playerService.getPlayerContext(friendId);
		if (playerContext == null) {
			return ErrorCode.CHAT_FRIEND_NOT_EXIST;
		}
		else if (senderId == friendId) {
			return ErrorCode.CHAT_TARGET_IS_SELF;
		}
		return ErrorCode.SUCCESS;
	}
	
	@Override
	public List<ChatDetail> getAllChatRecord(Player sender, long targetId) {
		if (targetId <= 0) {//获取所有好友聊天记录时,仅获取最新的那条
            List<ChatDetail> ret = Lists.newArrayList();
            //TODO nothing.
			return ret;
		}else {
			return super.getAllChatRecord(sender, targetId);
		}
	}
	
	@Override
	public Cache<BigInteger, Chat> getChatMap() {
		return chatRecordMap;
	}
	
}
