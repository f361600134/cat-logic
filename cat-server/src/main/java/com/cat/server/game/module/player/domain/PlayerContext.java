package com.cat.server.game.module.player.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.GameSession;
import com.cat.net.network.base.IProtocol;
import com.cat.net.network.base.Packet;

/**
 * 玩家上下文
 * 
 * @auth Jeremy
 * @date 2020年9月7日下午10:48:55
 */
public class PlayerContext{

	private static final Logger log = LoggerFactory.getLogger(PlayerContext.class);

	/** 玩家对象 */
	private Player player;
	/** 玩家会话 */
	private GameSession session;

	public final Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public GameSession getSession() {
		return session;
	}

	public void setSession(GameSession session) {
		this.session = session;
	}

	public PlayerContext() {
	}

	public PlayerContext(Player player) {
		this.player = player;
	}

	public PlayerContext(Player player, GameSession session) {
		this.player = player;
		this.session = session;
	}

	public void send(IProtocol protocol) {
		if (isOnline()) {
			session.send(Packet.encode(protocol));
			log.info("send message:[{}], playerId:{}, size={}B, data:{}", protocol.protocol(), player.getPlayerId(),
					protocol, protocol.toBytes().length);
		}
	}

	public boolean isOnline() {
		return session != null && session.isConnect();
	}

	public boolean isLoaded() {
		return player != null;
	}

	public long getPlayerId() {
		return player == null ? 0 : player.getPlayerId();
	}

	/**
	 * 获取账号
	 * @return 账号
	 */
	public String getAccountName() {
		return player == null ? "" : player.getAccountName();
	}

	/**
	 * 获取初始服务器id
	 * @return 服务器id
	 */
	public int getInitServerId() {
		return player == null ? 0 : player.getInitServerId();
	}

	/**
	 * 被挤掉
	 */
	public void forceLogout() {
		if(isOnline()) {
			return;
		}
		log.info("推送客户端消息, 下线 forceLogout");
		// AckUtility.ResponseDisconnect(56, this.channel);
	}

	public void setData() {

	}

}
