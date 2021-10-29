package com.cat.api.module.battle.response;

import com.cat.api.ProtocolId;
import com.cat.api.core.AbstractStuffProto;

/**
 * 通知玩家下线
 * @author Jeremy
 */
public class RespKickUpPlayer extends AbstractStuffProto {

	/**
	 * 被踢玩家id
	 */
	private int code;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int protocol() {
		return ProtocolId.RespKickUpPlayer;
	}
	
	public RespKickUpPlayer() {
	}
	
	public RespKickUpPlayer(int code) {
		this.code = code;
	}
	
	public static RespKickUpPlayer create(int code){
		return new RespKickUpPlayer(code);
	}

}
