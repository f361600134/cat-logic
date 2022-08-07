package com.cat.server.game.module.functioncontrol;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.functioncontrol.event.FunctionCheckReddotEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.eventbus.Subscribe;

@Component
class FunctionControlObserver implements IObserver{
	
	@Autowired private FunctionControlService service; 

    /**
     * 红点检测事件
     * @param event
     */
    @Subscribe
    public void onFunctionCheckReddotEvent(FunctionCheckReddotEvent event) {
    	service.checkReddot(event.getPlayerId(), event.getFunctionId());
    }


}
