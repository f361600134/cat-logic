package com.cat.server.game.module.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.function.event.FunctionCheckReddotEvent;
import com.google.common.eventbus.Subscribe;

/**
 * Function事件类
 */
@Component
public class FunctionObserver implements IObserver{

	@Autowired
    private FunctionService functionService;

	/**
     * 红点检测事件
     * @param event
     */
    @Subscribe
    public void onFunctionCheckReddotEvent(FunctionCheckReddotEvent event) {
    	functionService.onCheckReddot(event.getPlayerId(), event.getModuleId());
    }
}