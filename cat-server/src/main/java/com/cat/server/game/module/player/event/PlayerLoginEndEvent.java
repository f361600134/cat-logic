package com.cat.server.game.module.player.event;

import com.cat.server.core.event.PlayerBaseEvent;

public class PlayerLoginEndEvent extends PlayerBaseEvent {
	
	public static String ID = PlayerLoginEndEvent.class.getSimpleName();

    public PlayerLoginEndEvent(long playerId) {
        super(playerId);
    }
    
    public static PlayerLoginEndEvent create(long playerId) {
    	return new PlayerLoginEndEvent(playerId);
    }

}
