package com.cat.server.game.module.playermail.event;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.player.event.PlayerAfterLoginEvent;
import com.cat.server.game.module.player.event.PlayerLeaveGameEvent;
import com.cat.server.game.module.playermail.service.PlayerMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

/**
 * PlayerMail事件类
 */
@Component
public class PlayerMailObserver implements IObserver{

	@Autowired
    private PlayerMailService playerMailService;
	
	 /**
     * 登录事件
     * @param event
     */
    @Subscribe
    public void onPlayerAfterLoginEvent(PlayerAfterLoginEvent event) {
    	playerMailService.onLogin(event.getPlayerId());
    }
    
    /**
     * 离线事件
     * @param event
     */
    @Subscribe
    public void onPlayerLeaveGameEvent(PlayerLeaveGameEvent event) {
    	playerMailService.onLogout(event.getPlayerId());
    }


}