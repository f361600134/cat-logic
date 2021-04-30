package com.cat.server.game.module.player.event;

import com.cat.server.core.event.PlayerEventBase;

public class PlayerLoginEvent extends PlayerEventBase {
	
	public static String ID = PlayerLoginEvent.class.getSimpleName();

    public PlayerLoginEvent(long playerId) {
        super(playerId);
    }
    
    public static PlayerLoginEvent create(long playerId) {
    	return new PlayerLoginEvent(playerId);
    }

}
