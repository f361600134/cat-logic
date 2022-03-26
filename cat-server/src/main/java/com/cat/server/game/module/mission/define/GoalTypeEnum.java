package com.cat.server.game.module.mission.define;

/**
 * 所有任务类型
 * @author Jeremy
 *
 */
public enum GoalTypeEnum {
	//默认实现
	TYPE_DEFAULT(0),
	//登陆
	TYPE_LOGIN(1),
	;
	
	private final int type;
	
	private GoalTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	public static GoalTypeEnum getEnum(int completeType) {
		for(GoalTypeEnum type:values()) {
			if(type.getType()==completeType)
				return type;
		}
		return null;
	}
	
}
