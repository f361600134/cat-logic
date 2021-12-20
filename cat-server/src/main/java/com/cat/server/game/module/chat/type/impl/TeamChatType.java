package com.cat.server.game.module.chat.type.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.Chat;
import com.cat.server.game.module.chat.domain.ChatDetail;
import com.cat.server.game.module.chat.type.AbstractChatType;
import com.cat.server.game.module.team.ITeamService;
import com.cat.server.utils.Pair;
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
			//在给定时间内没有被读/写访问,则清除
			.expireAfterAccess(1, TimeUnit.HOURS)
			//最大条目,超过这个聊天记录, 根据LRU特点移除
			.maximumSize(100)
			.build();
	
	public TeamChatType() {
		super(ChannelType.TEAM.getChannel());
	}

	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
		//	私聊,通过发送方的玩家id,和目标玩家id,组装成唯一id
		ITeamService teamService = SpringContextHolder.getBean(ITeamService.class);
		long teamId = teamService.getPlayerTeamId(senderId);
		BigInteger bigInteger = BigInteger.valueOf(getChannel()).shiftLeft(64).add(BigInteger.valueOf(teamId));
		return bigInteger;
	}

	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
		//	获取到队伍, 然后通过队伍获取到成员
		Pair<Long, Long> pair = getOriginalIds(uniqueId);
		ITeamService teamService = SpringContextHolder.getBean(ITeamService.class);
		return teamService.getMemberIdsByTeamId(pair.getRight());
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
