package com.cat.server.game.module.chat.type;

import java.math.BigInteger;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.Chat;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 
 */
public class WorldChatType extends AbstractChatType {
	
	/**
	 * 聊天缓存, 所有频道聊天缓存, 使用Cache替代掉Map
	 * key:BigInteger, uniqueId, 唯一id
	 * value:聊天记录实体bean
	 */
	private Cache<BigInteger, Chat> chatRecordMap = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)// 在给定时间内没有被读/写访问,则清除
			.maximumSize(2)//	最大条目,超过这个聊天记录, 根据LRU特点移除
			.build();

	public WorldChatType() {
		super(ChannelType.WORLD.getChannel());
	}

	/**
	 * 世界聊天, 频道号即可, 保证id唯一即可
	 */
	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
		BigInteger bigInteger = BigInteger.valueOf(getChannel()).shiftLeft(64)
				.add(BigInteger.valueOf(getChannel()));
		return bigInteger;
	}

	/**
	 * 世界聊天,接收者为所有在线玩家
	 */
	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
		return playerService.getOnlinePlayerIds();
	}

	@Override
	public Cache<BigInteger, Chat> getChatMap() {
		return chatRecordMap;
	}

	@Override
	public Chat getChat(BigInteger uniqueId) {
		Chat chat = super.getChat(uniqueId);
		if (chat == null) {
			chat = new Chat();
			chat.setUniqueId(uniqueId);
			chat.setChannel(channel);
			chat.afterLoad();
			chatRecordMap.put(uniqueId, chat);
		}
		return chat;
	}
}
