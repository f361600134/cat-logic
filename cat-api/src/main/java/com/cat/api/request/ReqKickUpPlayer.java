package com.cat.api.request;

import com.cat.api.ProtocolId;
import com.cat.api.core.AbstractStuffProto;

/**
 * 通知玩家下线
 * @author Jeremy
 */
public class ReqKickUpPlayer extends AbstractStuffProto {

	/**
	 * 被踢玩家id
	 */
	private int playerId;
	/**
	 * 原因
	 */
	private String reason;
	
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int protocol() {
		return ProtocolId.ReqKickUpPlayer;
	}
	
	public ReqKickUpPlayer() {
	}
	
	public ReqKickUpPlayer(int playerId, String reason) {
		this.playerId = playerId;
		this.reason = reason;
	}
	
	public static ReqKickUpPlayer create(int playerId, String reason){
		return new ReqKickUpPlayer(playerId, reason);
	}

}
