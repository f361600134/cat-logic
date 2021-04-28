package com.cat.server.game.module.chat.type;

import java.math.BigInteger;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.Chat;
import com.cat.server.utils.Pair;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 
 */
public class FamilyChatType extends AbstractChatType {
	
	/**
	 * 聊天缓存, 所有频道聊天缓存, 使用Cache替代掉Map
	 * key:BigInteger, uniqueId, 唯一id
	 * value:聊天记录实体bean
	 */
	private Cache<BigInteger, Chat> chatRecordMap = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)// 在给定时间内没有被读/写访问,则清除
			.maximumSize(100)//	最大条目,超过这个聊天记录, 根据LRU特点移除
			.build();

	public FamilyChatType() {
		super(ChannelType.FAMILY.getChannel());
	}

	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
//		PlayerView playerView = PlayerManager.getInstance().getPlayerView(senderId);
//		long allianceId = playerView == null ? 0 : playerView.getAllianceId();
		long familyId = 0L;
		BigInteger bigInteger = BigInteger.valueOf(getChannel()).shiftLeft(64)
				.add(BigInteger.valueOf(familyId));
		return bigInteger;
	}

	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
//		Pair<Long, Long> pair = getOriginalIds(uniqueId);
//		Alliance alliance = AllianceManager.getInstance().getAlliance(pair.getRight());
//		if (alliance == null) {
//			return Collections.emptyList();
//		}
//		return alliance.getAllianceData().getMemberMap().keySet();
		return null;
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
