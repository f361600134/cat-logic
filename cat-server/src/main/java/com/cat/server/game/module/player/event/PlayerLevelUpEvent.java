package com.cat.server.game.module.player.event;

import com.cat.server.core.event.PlayerBaseEvent;

/**
 * 玩家升级事件
 * @author Jeremy Feng.
 */
public class PlayerLevelUpEvent extends PlayerBaseEvent {

	public static String ID = PlayerLevelUpEvent.class.getSimpleName();

	/**
	 * 升级前等级, 每升1级发一个事件
	 */
	private final int oldLevel;

	/**
	 * 当前等级
	 */
	private final int curLevel;

	public PlayerLevelUpEvent(long playerId, int oldLevel, int curLevel) {
		super(playerId);
		this.oldLevel = oldLevel;
		this.curLevel = curLevel;
	}

	public int getOldLevel() {
		return oldLevel;
	}

	public int getCurLevel() {
		return curLevel;
	}

	public static PlayerLevelUpEvent create(long playerId, int oldLevel, int curLevel) {
		return new PlayerLevelUpEvent(playerId, oldLevel, curLevel);
	}

}