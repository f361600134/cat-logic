package com.cat.server.game.module.mission.domain;

/**
 * 所有任务类型
 * @author Jeremy
 *
 */
public enum MissionGoalEnum {
	//默认实现
	TYPE_DEFAULT(0),
	//登陆
	TYPE_LOGIN(1),
	;
	
	private final int type;
	
	private MissionGoalEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	public static MissionGoalEnum getEnum(int completeType) {
		for(MissionGoalEnum type:values()) {
			if(type.getType()==completeType)
				return type;
		}
		return null;
	}
	
}
