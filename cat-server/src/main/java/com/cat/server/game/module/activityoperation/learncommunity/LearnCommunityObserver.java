package com.cat.server.game.module.activityoperation.learncommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.activity.event.ActivityStatusUpdateEvent;
import com.google.common.eventbus.Subscribe;

@Component
class LearnCommunityObserver implements IObserver{

    @Autowired private LearnCommunityService service;


    @Subscribe
    public void onActivityStatusUpdateEvent(ActivityStatusUpdateEvent event){
    	service.onActivityStatusUpdate(event);
    }

}

