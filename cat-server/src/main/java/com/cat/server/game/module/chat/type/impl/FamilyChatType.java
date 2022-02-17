package com.cat.server.game.module.chat.type.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.Chat;
import com.cat.server.game.module.chat.type.AbstractChatType;
import com.cat.server.game.module.family.IFamilyService;
import com.cat.server.utils.Pair;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 家族聊天实现类
 * @author Jeremy
 *
 */
public class FamilyChatType extends AbstractChatType {

	/**
	 * 聊天缓存, 所有频道聊天缓存, 使用Cache替代掉Map
	 * key:BigInteger, uniqueId, 唯一id
	 * value:聊天记录实体bean
	 */
	private Cache<BigInteger, Chat> chatRecordMap = CacheBuilder.newBuilder()
			//在给定时间内没有被读/写访问,则清除
			.expireAfterAccess(1, TimeUnit.HOURS)
			//最大条目,超过这个聊天记录, 根据LRU特点移除
			.maximumSize(100)
			.build();

	public FamilyChatType() {
		super(ChannelType.FAMILY.getChannel());
	}

	/**
	 * 获取家族聊天唯一id, 根据玩家id获取到家族id, 生成uniqueId
	 * @param senderId 发送者id
	 * @param targetId 目标id
	 * @return uniqueId
	 */
	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
		IFamilyService familyService = SpringContextHolder.getBean(IFamilyService.class);
		long familyId = familyService.getPlayerFamilyId(senderId);
		BigInteger bigInteger = BigInteger.valueOf(getChannel()).shiftLeft(64)
				.add(BigInteger.valueOf(familyId));
		return bigInteger;
	}

	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
		Pair<Long, Long> pair = getOriginalIds(uniqueId);
		IFamilyService familyService = SpringContextHolder.getBean(IFamilyService.class);
		return familyService.getMemberIdsByFamilyId(pair.getRight());
	}
	
	@Override
	public ErrorCode checkChat(long senderId, BigInteger uniqueId) {
		ErrorCode errorCode = super.checkChat(senderId, uniqueId);
		if (!errorCode.isSuccess()) {
			return errorCode;
		}
		Pair<Long, Long> pair = getOriginalIds(uniqueId);
		final long familyId = pair.getRight();
		if (familyId <= 0) {
			return ErrorCode.CHAT_ALLIANCE_NOT_EXIST;
		}
		return ErrorCode.SUCCESS;
	}

	@Override
	public Cache<BigInteger, Chat> getChatMap() {
		return chatRecordMap;
	}
	

}
