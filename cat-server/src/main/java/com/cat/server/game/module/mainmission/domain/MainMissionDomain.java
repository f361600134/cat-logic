package com.cat.server.game.module.mainmission.domain;

import java.util.Collection;
import java.util.List;

import com.cat.server.core.event.IEvent;
import com.cat.server.core.server.AbstractModuleDomain;
import com.cat.server.game.data.proto.PBMission.PBMissionInfo;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mission.handler.IMissionHandler;
import com.cat.server.game.module.mission.handler.MissionHandler;
import com.cat.server.game.module.mission.type.IMission;
import com.cat.server.game.module.mission.type.MainMissionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主线任务域
 * 
 * @author Jeremy
 *
 */
public class MainMissionDomain extends AbstractModuleDomain<Long, MainMission> implements IMissionHandler {

	private static final Logger log = LoggerFactory.getLogger(MainMissionDomain.class);

	// 代理处理代理类
	private MissionHandler<MainMissionType> missionHandler;
//	private MainMissionHandler missionHandler;

	public MainMissionDomain() {
	}

	/**
	 * 初次创建对象, 初始化第一条主线任务,
	 */
	@Override
	public void afterCreate() {
		int configId = 0;
		MainMissionType initNission = MainMissionType.create(configId);
		bean.getMissionData().addMissionPojo(initNission);
		bean.update();
	}

	@Override
	public void afterInit() {
//		this.missionHandler = Builder.newBuilder().
//				playerId(getPlayerId()).missionData(bean.getMissionData())
//				.afterRewardedListener((mission, missionData) -> {
//					// 主线任务完成后,需要接取下一条任务
//					// final int configId = mission.getConfigId();
//					// ConfigMission config = ConfigMissionMgr.getConfig(configId);
//					// int nextConfigId = config.getNextMissionId();
//					int nextConfigId = 0;
//					MainMissionType nextNission = MainMissionType.create(nextConfigId);
//					missionData.addMissionPojo(nextNission);
//				}).build();
		missionHandler = new MissionHandler<>(bean.getMissionData());
		missionHandler.setPlayerId(getPlayerId());
		//missionHandler.setMissionData(bean.getMissionData());
		missionHandler.setAfterRewardedListener((mission, missionData) -> {
					int nextConfigId = 0;
					MainMissionType nextNission = MainMissionType.create(nextConfigId);
					missionData.addMissionPojo(nextNission);
		}); 
	}
	
	@Override
	public ErrorCode onProcess(IEvent event) {
		ErrorCode errorCode = missionHandler.onProcess(event);
		if (errorCode.isSuccess()) {
			bean.update();
		}
		return errorCode;
	}

	@Override
	public ErrorCode onReward(int configId, NatureEnum nenum) {
		ErrorCode errorCode = missionHandler.onReward(configId, nenum);
		if (errorCode.isSuccess()) {
			bean.update();
		}
		return errorCode;
	}
	
	@Override
	public Collection<PBMissionInfo> toProto() {
		return missionHandler.toProto();
	}

	@Override
	public List<IMission> getUpdateList() {
		return missionHandler.getUpdateList();
	}

	@Override
	public long getPlayerId() {
		return getId();
	}

}