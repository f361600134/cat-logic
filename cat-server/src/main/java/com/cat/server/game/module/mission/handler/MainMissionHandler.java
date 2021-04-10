package com.cat.server.game.module.mission.handler;

import com.cat.server.game.module.mission.type.MainMissionType;
import com.cat.server.game.module.mission.type.MissionTypeData;

/**
 * 主线任务具体实现类,
 * @author Jeremy
 *
 */
public class MainMissionHandler extends AbstractMissionHandler<MainMissionType> {

	public MainMissionHandler() {
		super();
	}
	
	public MainMissionHandler(Builder builder) {
		super();
		this.playerId = builder.getPlayerId();
		this.missionData = builder.getMissionData();
		this.afterRewardedListener = builder.getAfterRewardedListener();
	}
	
	/**
	 * 	内部构造器
	 * @author Jeremy
	 *
	 */
	public static class Builder extends AbstractHandlerBuilder<MainMissionType> {
		
		public Builder playerId(long playerId) {
			this.playerId = playerId;
			return this;
		}

		public Builder missionData(MissionTypeData<MainMissionType> missionData) {
			this.missionData = missionData;
			return this;
		}

		public Builder afterRewardedListener(IMissionListener<MissionTypeData<MainMissionType>> afterRewardedListener) {
			this.afterRewardedListener = afterRewardedListener;
			return this;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public MainMissionHandler build() {
			return new MainMissionHandler(this);
		}

	}

}
