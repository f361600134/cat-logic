package com.cat.server.game.module.chat.type.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.Chat;
import com.cat.server.game.module.chat.domain.ChatDetail;
import com.cat.server.game.module.chat.type.AbstractChatType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 队伍聊天, 不保存聊天记录
 * 每个玩家只能有一个队伍, 所以
 * @author Jeremy
 *
 */
public class TeamChatType extends AbstractChatType {
	
	/**
	 * 队伍聊天缓存,不持久化
	 */
	private Cache<BigInteger, Chat> chatRecordMap = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)// 在给定时间内没有被读/写访问,则清除
			.maximumSize(100)//	最大条目,超过这个聊天记录, 根据LRU特点移除
			.build();
	
	public TeamChatType() {
		super(ChannelType.TEAM.getChannel());
	}

	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
		//	私聊,通过发送方的玩家id,和目标玩家id,组装成唯一id
//		long tempId = targetId;
//		if (senderId < tempId) {
//			senderId ^= tempId;
//			tempId ^= senderId;
//			senderId ^= tempId;
//		}
//		BigInteger bigInteger = BigInteger.valueOf(senderId).shiftLeft(64).add(BigInteger.valueOf(tempId));
		long teamId = 0L;
		BigInteger bigInteger = BigInteger.valueOf(getChannel()).shiftLeft(64).add(BigInteger.valueOf(teamId));
		return bigInteger;
	}

	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
		//	获取到队伍, 然后通过队伍获取到成员
		return null;
	}

	@Override
	public Cache<BigInteger, Chat> getChatMap() {
		//	没有历史聊天记录
		return chatRecordMap;
	}
	
	@Override
	public void beforeSendMsg(BigInteger uniqueId, ChatDetail chatDetail) {
		//	缓存但不持久化
	}

}
