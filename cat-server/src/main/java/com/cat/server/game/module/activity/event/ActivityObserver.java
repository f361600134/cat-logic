package com.cat.server.game.module.activity.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.activity.ActivityService;

/**
 * Activity事件类
 */
@Component
public class ActivityObserver implements IObserver{

	@Autowired
    private ActivityService activityService;


}