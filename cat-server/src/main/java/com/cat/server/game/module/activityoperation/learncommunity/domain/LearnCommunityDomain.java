package com.cat.server.game.module.activityoperation.learncommunity.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.IEvent;
import com.cat.server.core.server.AbstractModuleDomain;
import com.cat.server.game.data.config.local.ConfigLearnCommunityMission;
import com.cat.server.game.data.proto.PBMission.PBMissionInfo;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mission.handler.IMissionHandler;
import com.cat.server.game.module.mission.handler.MissionHandler;
import com.cat.server.game.module.mission.type.IMission;
import com.cat.server.game.module.mission.type.impl.LearnCommunityMissionType;
import com.cat.server.utils.TimeUtil;

/**
* LearnCommunityDomain
* @author Jeremy
*/
public class LearnCommunityDomain extends AbstractModuleDomain<Long, LearnCommunity> implements IMissionHandler{

	private static final Logger log = LoggerFactory.getLogger(LearnCommunityDomain.class);
	
	// 代理处理代理类
	private MissionHandler<LearnCommunityMissionType> missionHandler;
	
	public LearnCommunityDomain(){
	}
	
	////////////业务代码////////////////////
	
	/**
	 * 检测每日重置
	 * @return void  
	 * @date 2021年9月30日上午8:45:37
	 */
	public boolean checkAndReset() {
		long now = TimeUtil.now();
		if (!TimeUtil.isSameDay(now, bean.getLastResetTime())) {
			return false;
		}
		bean.setLastResetTime(now);
		bean.setTodayExp(0);
		bean.getDailyActiveMap().clear();
		bean.update();
		return true;
	}
	
	/**
	 * 清除玩家的研习社信息
	 */
	public void clear() {
		bean.setTodayExp(0);
		bean.setExp(0);
		bean.setActivityId(0);
		bean.setExclusive(false);
		bean.setLevel(0);
		
		bean.setLastResetTime(0);
		bean.getDailyActiveMap().clear();
		bean.getMissionData().clear();
		bean.getRewardDataMap().clear();
		bean.update();
	}
	
	/////////接口类实现///////////
	/**
	 * 初次创建对象, 初始化任务,
	 */
	@Override
	public void afterCreate() {
		//默认接取所有任务
		Map<Integer, ConfigLearnCommunityMission> configs = ConfigManager.getInstance().getAllConfigs(ConfigLearnCommunityMission.class);
		configs.forEach((configId, config)->{
			LearnCommunityMissionType mission = LearnCommunityMissionType.create(configId);
			bean.getMissionData().addMissionPojo(mission);
		});
		bean.update();
	}

	@Override
	public void afterInit() {
		missionHandler = new MissionHandler<>(bean.getMissionData());
		missionHandler.setPlayerId(getPlayerId());
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

