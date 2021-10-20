package com.cat.server.game.module.mainmission;

import com.cat.server.core.event.IObserver;
import com.cat.server.core.event.PlayerBaseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class MainMissionObserver implements IObserver {
	
	@Autowired private MainMissionService missionService;
	
	/**
	 * 任务默认接收所有事件,然后在任务内进行事件判断
	 * @param event
	 */
	public void onEvent(PlayerBaseEvent event){
		missionService.onEvent(event);
	}
	
}
