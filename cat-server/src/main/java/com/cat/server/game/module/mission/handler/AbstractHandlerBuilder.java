package com.cat.server.game.module.mission.handler;

import com.cat.server.game.module.mission.type.IMission;
import com.cat.server.game.module.mission.type.MissionTypeData;

/**
 * 任务处理器的构造器抽象类
 * @author Jeremy
 * @param <T>
 */
abstract class AbstractHandlerBuilder<T extends IMission> {
	long playerId;
	MissionTypeData<T> missionData;
	IMissionListener<MissionTypeData<T>> afterRewardedListener;
	
	long getPlayerId() {
		return playerId;
	}
	MissionTypeData<T> getMissionData() {
		return missionData;
	}
	IMissionListener<MissionTypeData<T>> getAfterRewardedListener() {
		return afterRewardedListener;
	}
}
