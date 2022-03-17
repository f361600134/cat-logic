package com.cat.server.game.module.shop;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.player.event.PlayerLoginEvent;
import com.google.common.eventbus.Subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shop事件类
 */
@Component
public class ShopObserver implements IObserver {

	@Autowired
	private ShopService shopService;

	@Subscribe
	public void onLogin(PlayerLoginEvent event) {
		shopService.onLogin(event.getPlayerId(), event.getTime());
	}
	
	@Subscribe
	public void onLogout(PlayerLoginEvent event) {
		shopService.onLogout(event.getPlayerId());
	}

}