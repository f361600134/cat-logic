package com.cat.server.game.module.mission.handler;

import com.cat.server.game.module.mission.type.MissionTypeData;
import com.cat.server.game.module.mission.type.impl.ArtifactMissionType;

/**
 * 主线任务具体实现类,
 * @author Jeremy
 *
 */
public class ArtifactMissionHandler extends AbstractMissionHandler<ArtifactMissionType> {

	public ArtifactMissionHandler() {
		super();
	}
	
	public ArtifactMissionHandler(Builder builder) {
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
	public static class Builder extends AbstractHandlerBuilder<ArtifactMissionType> {
		
		public Builder playerId(long playerId) {
			this.playerId = playerId;
			return this;
		}

		public Builder missionData(MissionTypeData<ArtifactMissionType> missionData) {
			this.missionData = missionData;
			return this;
		}

		public Builder afterRewardedListener(IMissionListener<MissionTypeData<ArtifactMissionType>> afterRewardedListener) {
			this.afterRewardedListener = afterRewardedListener;
			return this;
		}
//
		public static Builder newBuilder() {
			return new Builder();
		}

		public ArtifactMissionHandler build() {
			return new ArtifactMissionHandler(this);
		}

	}

}
