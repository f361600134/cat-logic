package com.cat.server.game.module.pet;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.player.event.PlayerLeaveGameEvent;
import com.cat.server.game.module.player.event.PlayerLoginEvent;
import com.google.common.eventbus.Subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Pet事件类
 */
@Component
public class PetObserver implements IObserver {

	@Autowired
	private PetService shopService;

	@Subscribe
	public void onLogin(PlayerLoginEvent event) {
		shopService.onLogin(event.getPlayerId(), event.getTime());
	}
	
	@Subscribe
	public void onLogout(PlayerLeaveGameEvent event) {
		shopService.onLogout(event.getPlayerId());
	}

}