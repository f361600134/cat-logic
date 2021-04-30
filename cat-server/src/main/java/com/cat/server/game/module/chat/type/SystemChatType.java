package com.cat.server.game.module.chat.type;

import java.math.BigInteger;
import java.util.Collection;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.Chat;
import com.cat.server.game.module.chat.domain.ChatDetail;
import com.google.common.cache.Cache;


/**
 * 系统聊天, 仅作转发作用,不能实质聊天.
 * 不缓存, 不持久化
 */
public class SystemChatType extends AbstractChatType {

	public SystemChatType() {
		super(ChannelType.SYSTEM.getChannel());
	}
	
	/**
	 * 系统聊天, 频道号即可, 保证id唯一即可
	 */
	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
		BigInteger bigInteger = BigInteger.valueOf(getChannel()).shiftLeft(64)
				.add(BigInteger.valueOf(getChannel()));
		return bigInteger;
	}

	/**
	 * 系统聊天,接收者为所有在线玩家
	 */
	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
		return playerService.getPlayerIds();
	}
	
	/**
	 * 系统频道, 不能由玩家直接进行聊天.
	 * 一般由玩家游戏内某个模块触发(如获得极品装备), 通过系统频道进行内容推送,
	 *  或者由GM后台推送.(如发送公告)
	 *  所以默认成功成功.
	 */
	@Override
	public ErrorCode checkChat(long senderId, BigInteger uniqueId) {
		if (senderId > 0) {
			return ErrorCode.CHAT_SYSTEM_NOT_ALLOWED;
		}
		return ErrorCode.SUCCESS;
	}

	@Override
	public Cache<BigInteger, Chat> getChatMap() {
		return null;
	}
	
	@Override
	public void beforeSendMsg(BigInteger uniqueId, ChatDetail chatDetail) {
		//TODO nothing.
	}

}
