package com.cat.server.game.module.mission.domain;

public enum MissionTypeEnum {

	// 默认实现
	MAIN(0),
	// 登陆
	LEARN_COMMUNITY(1),;

	private final int type;

	private MissionTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static MissionTypeEnum getEnum(int completeType) {
		for (MissionTypeEnum type : values()) {
			if (type.getType() == completeType)
				return type;
		}
		return null;
	}

}
