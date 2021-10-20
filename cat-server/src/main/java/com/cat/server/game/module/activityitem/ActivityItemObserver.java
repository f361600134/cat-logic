package com.cat.server.game.module.activityitem;

import com.cat.server.core.event.IObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ActivityItem事件类
 */
@Component
public class ActivityItemObserver implements IObserver{

	@Autowired
    private ActivityItemService activityItemService;


}