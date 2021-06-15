package com.cat.server.game.module.player.domain;

import com.cat.server.core.server.IPersistence;

/**
* @author Jeremy
*/
@PO(name = "player")
public class Player extends PlayerPo implements IPersistence{

	public Player() {

	}
	
	public Player(long playerId) {
		this.playerId = playerId;
	}
	
	@Override
	public Object key() {
		//TODO
		//return getPlayerId();
	}
	
	public String keyColumn() {
		//TODO 
		//return PROP_PLAYERID;
	}
	
}
