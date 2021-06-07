package com.cat.server.game.module.mission.type;


import com.cat.server.game.module.mission.type.${entity.getEntityName()}MissionType;
import org.coral.server.game.module.mission.type.MissionTypeData;

/**
 * ${entity.getEntityName()}任务处理器
 * @author Jeremy
 */
public class ${entity.getEntityName()}MissionHandler extends AbstractMissionHandler<${entity.getEntityName()}MissionType> {

	public ${entity.getEntityName()}MissionHandler() {
		super();
	}
	
	public ${entity.getEntityName()}MissionHandler(Builder builder) {
		super();
		this.playerId = builder.getPlayerId();
		this.missionData = builder.getMissionData();
		this.afterRewardedListener = builder.getAfterRewardedListener();
	}
	
	/**
	 * 	内部构造器
	 * @author Jeremy
	 */
	public static class Builder extends AbstractHandlerBuilder<${entity.getEntityName()}MissionType> {
		
		public Builder playerId(long playerId) {
			this.playerId = playerId;
			return this;
		}

		public Builder missionData(MissionTypeData<${entity.getEntityName()}MissionType> missionData) {
			this.missionData = missionData;
			return this;
		}

		public Builder afterRewardedListener(IMissionListener<MissionTypeData<${entity.getEntityName()}MissionType>> afterRewardedListener) {
			this.afterRewardedListener = afterRewardedListener;
			return this;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public ${entity.getEntityName()}MissionHandler build() {
			return new ${entity.getEntityName()}MissionHandler(this);
		}

	}

}

