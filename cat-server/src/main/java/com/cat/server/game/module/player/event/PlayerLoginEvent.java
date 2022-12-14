package com.cat.server.game.module.player.event;

import com.cat.server.core.event.PlayerBaseEvent;

public class PlayerLoginEvent extends PlayerBaseEvent {
	
	public static String ID = PlayerLoginEvent.class.getSimpleName();

    public PlayerLoginEvent(long playerId) {
        super(playerId);
    }
    
    public static PlayerLoginEvent create(long playerId) {
    	return new PlayerLoginEvent(playerId);
    }

}
