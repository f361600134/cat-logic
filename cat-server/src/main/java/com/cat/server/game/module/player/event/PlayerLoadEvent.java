package com.cat.server.game.module.player.event;

import com.cat.server.core.event.PlayerEventBase;

/**
 * 从数据库加载了玩家的事件
 */
public class PlayerLoadEvent extends PlayerEventBase {
	
	public static String ID = PlayerLoadEvent.class.getSimpleName();

    public PlayerLoadEvent(long playerId) {
        super(playerId);
    }
    
    public static PlayerLoadEvent create(long playerId) {
    	return new PlayerLoadEvent(playerId);
    }
}
