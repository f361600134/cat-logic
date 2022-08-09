package com.cat.server.game.module.function.event;

import com.cat.server.core.event.PlayerBaseEvent;

/**
 * 功能红点检测事件, 有红点检测时, 发送这个事件
 * @auth Jeremy
 * @date 2022年8月7日下午8:14:59
 */
public class FunctionCheckReddotEvent extends PlayerBaseEvent {
	
	public static String ID = FunctionCheckReddotEvent.class.getSimpleName();

	//红点条件枚举
    private final int moduleId; 

    private FunctionCheckReddotEvent(long playerId, int moduleId) {
    	super(playerId);
    	this.moduleId = moduleId;
    }

    public static FunctionCheckReddotEvent create(long playerId, int moduleId){
        return new FunctionCheckReddotEvent(playerId, moduleId);
    }

	public int getModuleId() {
		return moduleId;
	}

    
}
