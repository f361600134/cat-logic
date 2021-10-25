package com.cat.server.game.module.player;

import java.util.Collection;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.module.player.domain.PlayerContext;

public interface IPlayerService {
	
	/**
	 * 获取所有在线玩家
	 * @return
	 */
	public Collection<Long> getOnlinePlayerIds();
	
	/**
	 * 获取获取玩家上下文
	 * @return
	 */
	public PlayerContext getPlayerContext(Long playerId);
	
	/**
	 * 获取获取玩家上下文
	 * @return
	 */
	public Collection<PlayerContext> getPlayerContexts();
	
	/**
	 * 发送消息
	 * @param playerId 玩家id
	 * @param protocol 发送的消息
	 */
	public void sendMessage(long playerId, IProtocol protocol);
	
	/**
	 * 群发送消息
	 * @param playerIds 玩家id列表
	 * @param protocol 发送的消息
	 */
	public void sendMessage(Collection<Long> playerIds, IProtocol protocol);
	
	/**
	 * 群发送消息给所有在线玩家
	 * @param protocol 发送的消息
	 */
	public void sendMessageToAll(IProtocol protocol);
	
	/**
	 * 踢指定玩家下线
	 * @param playerIds 玩家id列表
	 */
	public void kickPlayer(Collection<Long> playerIds);
	
//	/**
//	 * 通过session获取玩家id
//	 * @param session
//	 * @return
//	 */
//	public Long getPlayerId(ISession session);
//	
//	/**
//	 * 通过session获取玩家对象
//	 * @param session
//	 * @return
//	 */
//	public PlayerContext getPlayerContext(ISession session);

}
