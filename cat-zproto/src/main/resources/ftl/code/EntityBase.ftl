package com.kepe.dragon.persistent.domain;

import com.cat.server.core.event.IObserver;
import com.cat.server.core.event.PlayerEventBase;
import com.cat.server.game.module.${entityName?lower_case}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ${entityName}Observer implements IObserver {
	
	@Autowired private ${entityName}Service ${entityName?uncap_first}Service;
	
	/**
	 * 	任务默认接收所有事件,然后在任务内进行事件判断
	 * @param event
	 */
	public void onEvent(PlayerEventBase event){
		${entityName?uncap_first}Service.onEvent(event);
	}
	
}