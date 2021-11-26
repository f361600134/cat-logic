package com.cat.server.game.module.mail.assist;

/**
 * 状态信息
 * 状态0, 不与其他状态并存
 * @author Jeremy
 */
public enum MailState {
	
	NONE(0),//未读取
    READ(1),//已读取
    REWARD(2),//已领奖
	DELETE(4);//删除
    
    private final int state;
    
    MailState(int state){
    	this.state = state;
    }

	public int getState() {
		return state;
	}
 
}
