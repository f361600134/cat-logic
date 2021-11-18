package com.cat.server.game.module.mission.domain;

/**
 * 所有任务类型
 * @author Jeremy
 *
 */
public enum MissionEnum {
	//默认实现
	TYPE_DEFAULT(0),
	//登陆
	TYPE_LOGIN(1),
	;
	
	private final int type;
	
	private MissionEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	public static MissionEnum getEnum(int completeType) {
		for(MissionEnum type:values()) {
			if(type.getType()==completeType)
				return type;
		}
		return null;
	}
	
}
