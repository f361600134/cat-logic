package com.cat.server.game.module.item.event;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.item.service.ItemService;
import com.cat.server.game.module.player.event.PlayerLoginEndEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

@Component
public class ItemObserver implements IObserver{

    @Autowired
    private ItemService itemService;

    /**
     * 登录事件
     * @param event
     */
    @Subscribe
    public void onAfterLogin(PlayerLoginEndEvent event) {
//        itemService.onLogin(event.getPlayerId());
    }


}
