package com.cat.server.game.module.playermail.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.player.event.PlayerLeaveGameEvent;
import com.cat.server.game.module.playermail.PlayerMailService;
import com.google.common.eventbus.Subscribe;

/**
 * PlayerMail事件类
 */
@Component
public class PlayerMailObserver implements IObserver{

	@Autowired
    private PlayerMailService playerMailService;
	
    /**
     * 离线事件
     * @param event
     */
    @Subscribe
    public void onPlayerLeaveGameEvent(PlayerLeaveGameEvent event) {
    	playerMailService.onLogout(event.getPlayerId());
    }

}