package com.cat.server.game.module.functioncontrol.event;

import com.cat.server.core.event.PlayerBaseEvent;
import com.cat.server.game.module.functioncontrol.define.ReddotConditionEnum;

/**
 * 功能红点检测事件
 * @auth Jeremy
 * @date 2022年8月7日下午8:14:59
 */
public class FunctionCheckReddotEvent extends PlayerBaseEvent {
	
	public static String ID = FunctionCheckReddotEvent.class.getSimpleName();

	//红点条件枚举
    private final ReddotConditionEnum reddotConditionEnum; 

    private FunctionCheckReddotEvent(long playerId, ReddotConditionEnum reddotConditionEnum) {
    	super(playerId);
    	this.reddotConditionEnum = reddotConditionEnum;
    }

    public static FunctionCheckReddotEvent create(long playerId, ReddotConditionEnum reddotConditionEnum){
        return new FunctionCheckReddotEvent(playerId, reddotConditionEnum);
    }

	public ReddotConditionEnum getFunctionId() {
		return reddotConditionEnum;
	}

    
}
