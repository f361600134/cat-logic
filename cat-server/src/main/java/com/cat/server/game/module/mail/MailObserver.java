package com.cat.server.game.module.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.player.event.PlayerLoginEndEvent;
import com.google.common.eventbus.Subscribe;

/**
 * PlayerMail事件类
 */
@Component
public class MailObserver implements IObserver{

	@Autowired
    private MailService mailService;
	
	 /**
     * 登录事件
     * @param event
     */
    @Subscribe
    public void onPlayerAfterLoginEvent(PlayerLoginEndEvent event) {
    	mailService.onLogin(event.getPlayerId());
    }
    
//    /**
//     * 离线事件
//     * @param event
//     */
//    @Subscribe
//    public void onPlayerLeaveGameEvent(PlayerLeaveGameEvent event) {
//    	mailService.onLogout(event.getPlayerId());
//    }


}