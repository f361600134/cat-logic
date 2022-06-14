package com.cat.server.game.module.equip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.player.event.PlayerLeaveGameEvent;
import com.cat.server.game.module.player.event.PlayerLoginEvent;
import com.google.common.eventbus.Subscribe;

/**
 * Equip事件类
 */
@Component
public class EquipObserver implements IObserver{

	@Autowired
    private EquipService equipService;

	@Subscribe
	public void onLogin(PlayerLoginEvent event) {
		equipService.onLogin(event.getPlayerId(), event.getTime());
	}
	
	@Subscribe
	public void onLogout(PlayerLeaveGameEvent event) {
		equipService.onLogout(event.getPlayerId());
	}
}