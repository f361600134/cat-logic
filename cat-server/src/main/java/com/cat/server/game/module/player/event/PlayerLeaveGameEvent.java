package com.cat.server.game.module.player.event;

import com.cat.server.core.event.PlayerEventBase;

/**
 * 玩家离开游戏事件
 * 玩家断线后1分钟, 才视为真正离线,
 */
public class PlayerLeaveGameEvent extends PlayerEventBase {
	
	public static String ID = PlayerLeaveGameEvent.class.getSimpleName();
	
    public PlayerLeaveGameEvent(long playerId) {
        super(playerId);
    }
    
    public static PlayerLeaveGameEvent create(long playerId) {
    	return new PlayerLeaveGameEvent(playerId);
    }

}