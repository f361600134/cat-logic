package com.cat.server.game.module.activityoperation.learncommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;
import com.cat.server.core.event.PlayerBaseEvent;
import com.cat.server.game.module.activity.event.ActivityStatusUpdateEvent;
import com.google.common.eventbus.Subscribe;

@Component
class LearnCommunityObserver implements IObserver{

    @Autowired private LearnCommunityService service;


    @Subscribe
    public void onActivityStatusUpdateEvent(ActivityStatusUpdateEvent event){
    	service.onActivityStatusUpdate(event);
    }

    /**
	 * 任务默认接收所有事件,然后在任务内进行事件判断
	 * @param event
	 */
	public void onEvent(PlayerBaseEvent event){
		service.onEvent(event);
	}

}

