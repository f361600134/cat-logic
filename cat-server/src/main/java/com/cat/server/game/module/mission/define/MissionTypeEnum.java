package com.cat.server.game.module.mission.define;

public enum MissionTypeEnum {

	// 主线
	MAIN(1),
	// 登陆
	LEARN_COMMUNITY(2),
	;

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
