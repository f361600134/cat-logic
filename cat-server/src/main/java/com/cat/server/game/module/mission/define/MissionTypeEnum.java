package com.cat.server.game.module.mission.define;

public enum MissionTypeEnum {

	// 主线
	MAIN(1),
	// 活动-研习社
	LEARN_COMMUNITY(2),
	// 神器
	ARTIFACT(3),
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
