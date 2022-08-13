package com.cat.server.game.module.mission;

import com.cat.server.core.event.IObserver;
import com.cat.server.core.event.PlayerBaseEvent;
import com.cat.server.game.module.mission.MissionService;
import com.google.common.eventbus.Subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mission事件类
 */
@Component
public class MissionObserver implements IObserver{

	@Autowired
    private MissionService missionService;
	
	/**
	 * 红点检测事件
	 * @param event FunctionCheckReddotEvent
	 */
	@Subscribe
	public void onPlayerEvent(PlayerBaseEvent event) {
		missionService.onEvent(event);
	}


}