package com.cat.server.game.module.activity.type;

import com.cat.server.core.annotation.NotUse;

/**
 * 活动玩家数据
 * @author Jeremy
 */
@NotUse
@Deprecated
public abstract class AbstractActivityPlayerData implements IActivityPlayerData {

	/**
	 * 玩家id
	 */
	protected long playerId;

	public AbstractActivityPlayerData() {

	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	@Override
	public long getPlayerId() {
		return this.playerId;
	}
}
