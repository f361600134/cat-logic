package com.cat.server.game.module.mission.type;


import com.cat.server.game.module.mission.type.${entityName}MissionType;
import org.coral.server.game.module.mission.type.MissionTypeData;

/**
 * ${entityName}任务处理器
 * @author Jeremy
 */
public class ${entityName}MissionHandler extends AbstractMissionHandler<${entityName}MissionType> {

	public ${entityName}MissionHandler() {
		super();
	}
	
	public ${entityName}MissionHandler(Builder builder) {
		super();
		this.playerId = builder.getPlayerId();
		this.missionData = builder.getMissionData();
		this.afterRewardedListener = builder.getAfterRewardedListener();
	}
	
	/**
	 * 	内部构造器
	 * @author Jeremy
	 */
	public static class Builder extends AbstractHandlerBuilder<${entityName}MissionType> {
		
		public Builder playerId(long playerId) {
			this.playerId = playerId;
			return this;
		}

		public Builder missionData(MissionTypeData<${entityName}MissionType> missionData) {
			this.missionData = missionData;
			return this;
		}

		public Builder afterRewardedListener(IMissionListener<MissionTypeData<${entityName}MissionType>> afterRewardedListener) {
			this.afterRewardedListener = afterRewardedListener;
			return this;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public ${entityName}MissionHandler build() {
			return new ${entityName}MissionHandler(this);
		}

	}

}

