package com.cat.server.game.module.player.event;

import com.cat.server.core.event.PlayerEventBase;

/**
 * 从数据库加载了玩家的事件
 *  @deprecated 弃用此事件, 初始化数据时无需通知其他模块初始化事件
 *  其他模块加载数据时, 如果无数据, 则视为第一次加载, 初始化数据
 */
public class PlayerLoadEndEvent extends PlayerEventBase {
	
	public static String ID = PlayerLoadEndEvent.class.getSimpleName();

    public PlayerLoadEndEvent(long playerId) {
    	super(playerId);
    }
    
    public static PlayerLoadEndEvent create(long playerId) {
    	return new PlayerLoadEndEvent(playerId);
    }
}
