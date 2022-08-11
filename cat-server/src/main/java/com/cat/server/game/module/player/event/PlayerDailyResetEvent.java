package com.cat.server.game.module.player.event;

import com.cat.server.core.event.PlayerBaseEvent;

/**
 * 玩家日重置事件<br>
 * 玩家处理{@link DailyResetEvent}后抛出的事件<br>
 * 可用于登录天数相关的任务 避免多个模块处理事件乱序导致获取累计登陆天数错误
 * @author Jeremy
 */
public class PlayerDailyResetEvent extends PlayerBaseEvent {
	
    public static final String ID = PlayerDailyResetEvent.class.getSimpleName();
    
    public PlayerDailyResetEvent(long playerId) {
        super(playerId);
    }
    
    public static PlayerDailyResetEvent create(long playerId) {
    	return new PlayerDailyResetEvent(playerId);
    }
    
}
