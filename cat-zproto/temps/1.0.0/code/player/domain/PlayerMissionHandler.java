package com.cat.server.game.module.mission.type;


import com.cat.server.game.module.mission.type.PlayerMissionType;
import org.coral.server.game.module.mission.type.MissionTypeData;

/**
 * Player任务处理器
 * @author Jeremy
 */
public class PlayerMissionHandler extends AbstractMissionHandler<PlayerMissionType> {

	public PlayerMissionHandler() {
		super();
	}
	
	public PlayerMissionHandler(Builder builder) {
		super();
		this.playerId = builder.getPlayerId();
		this.missionData = builder.getMissionData();
		this.afterRewardedListener = builder.getAfterRewardedListener();
	}
	
	/**
	 * 	内部构造器
	 * @author Jeremy
	 */
	public static class Builder extends AbstractHandlerBuilder<PlayerMissionType> {
		
		public Builder playerId(long playerId) {
			this.playerId = playerId;
			return this;
		}

		public Builder missionData(MissionTypeData<PlayerMissionType> missionData) {
			this.missionData = missionData;
			return this;
		}

		public Builder afterRewardedListener(IMissionListener<MissionTypeData<PlayerMissionType>> afterRewardedListener) {
			this.afterRewardedListener = afterRewardedListener;
			return this;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public PlayerMissionHandler build() {
			return new PlayerMissionHandler(this);
		}

	}

}

