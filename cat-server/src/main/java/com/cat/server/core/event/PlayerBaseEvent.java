package com.cat.server.core.event;

/**
 * 玩家事件基础类<br>
 */
public abstract class PlayerBaseEvent extends BaseEvent{
	
	/**
	 * 玩家id<br>
	 * 玩家id可变, 适用于重复发送的事件<br>
	 * 避免同一个事件重复被创建
	 */
    private long playerId;

    public PlayerBaseEvent() {}
    
    public PlayerBaseEvent(long playerId) {
    	super();
    	this.playerId = playerId;
    }
    
    public PlayerBaseEvent(long playerId, long time) {
    	super(time);
    	this.playerId = playerId;
    }

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
}
