package com.cat.server.game.module.mission2.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.config.local.ext.IConfigMission;
import com.cat.server.game.data.proto.PBMission.PBMissionInfo;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.mission2.domain.MissionState;
import com.cat.server.game.module.mission2.goal.GoalTypeManager;
import com.cat.server.game.module.mission2.goal.IMissionGoalType;
import com.cat.server.game.module.mission2.proto.PBMissionInfoBuilder;
import com.cat.server.game.module.mission2.proto.RespMissionInfoBuilder;
import com.cat.server.game.module.mission2.type.Mission;
import com.cat.server.game.module.mission2.type.MissionGoal;
import com.cat.server.game.module.mission2.type.MissionTypeData;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.utils.TimeUtil;

public abstract class AbstractMissionHandler<T extends IConfigMission> implements IMissionHandler<T> {

	@Autowired
	private GoalTypeManager goalTypeManager;
	@Autowired
	private IPlayerService playerService;
	@Autowired
	private IResourceGroupService resourceGroupService;

	private Class<T> missionConfigClazz;

	@SuppressWarnings("unchecked")
	public AbstractMissionHandler() {
		Type superClass = getClass().getGenericSuperclass();
		this.missionConfigClazz = (Class<T>) (((ParameterizedType) superClass).getActualTypeArguments()[0]);
	}

	@Override
	public T getConfig(int configId) {
		return ConfigManager.getInstance().getConfig(missionConfigClazz, configId);
	}

	@Override
	public Map<Integer, T> getConfigs() {
		return ConfigManager.getInstance().getAllConfigs(missionConfigClazz);
	}

	@Override
	public boolean checkAndRefresh(long playerId, long now, boolean notify) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultCodeData<Mission> accept(long playerId, int missionId, long now) {
		T missionConfig = getConfig(missionId);
		if (missionConfig == null) {
			return ResultCodeData.of(ErrorCode.CONFIG_NOT_EXISTS);
		}
		ErrorCode resultCode = checkAccept(playerId, missionId, now);
		if (resultCode != null) {
			return ResultCodeData.of(resultCode);
		}
		Mission mission = buildMission(missionConfig);
		if (mission == null) {
			return ResultCodeData.of(ErrorCode.CONFIG_NOT_EXISTS);
		}
		// 领取任务 注册事件
		doAcceptMission(playerId, mission, now);
		return ResultCodeData.of(mission);
	}

	/**
	 * 检测是否可以接任务
	 * 
	 * @param playerId  玩家id
	 * @param missionId 任务配置id
	 * @param now       当前时间
	 * @return ErrorCode
	 */
	protected ErrorCode checkAccept(long playerId, int missionId, long now) {
		return ErrorCode.SUCCESS;
	}

	/**
	 * 构建任务对象
	 * 
	 * @return
	 * @return Mission
	 * @date 2022年3月19日下午7:06:08
	 */
	protected Mission buildMission(T config) {
		// 初始化任務目標
		int[] completeTypes = config.getCompleteType();
		List<MissionGoal> goalList = new ArrayList<>();
		for (int i = 0; i < completeTypes.length; i++) {
			int completeType = completeTypes[i];
			MissionGoal goal = new MissionGoal();
			goal.setIndex(0);
			goal.setType(completeType);
			goal.setStatus(MissionState.STATE_NONE.getValue());
			goalList.add(goal);
		}
		Mission mission = new Mission(config.getId());
		mission.setSourceType((short) this.getMissionType().getType());
		mission.setGoals(goalList);
		return mission;
	}

//	protected Mission buildMission(T missionConfig) {
//		Mission mission = new Mission();
//		mission.setId(missionConfig.getSid());
//		mission.setSourceType(getKey());
//		mission.setStatus(MissionStatus.ACCEPT);
//		int goalType = this.getConfigTargetType(missionConfig);

//		List<MissionGoal> goalList = new ArrayList<>();
//		goalList.add(goal);
//		mission.setGoals(goalList);
//		return mission;
//	}

	protected void doAcceptMission(long playerId, Mission mission, long now) {
		// 记录
		MissionTypeData missionSourceTypeData = this.getMissionTypeData(playerId, true);
		Map<Integer, Mission> missions = missionSourceTypeData.getMissions();
		missions.put(mission.getConfigId(), mission);
		missionSourceTypeData.setLastTime(now);
		// 尝试刷新任务状态
		refreshMission(playerId, mission);
	}

	@Override
	public boolean refreshMission(long playerId, Mission mission) {
		if (mission.getState() != MissionState.STATE_NONE.getValue()) {
			return false;
		}
		List<MissionGoal> goals = mission.getGoals();
		if (goals == null || goals.isEmpty()) {
			return false;
		}
		int missionId = mission.getConfigId();
		T missionConfig = getConfig(missionId);
		if (missionConfig == null) {
			return false;
		}
		boolean change = false;
		boolean allGoalComplete = true;
		for (int i = 0; i < goals.size(); i++) {
			MissionGoal goal = goals.get(i);
			if (goal.getStatus() == MissionState.STATE_COMPLETE.getValue()) {
				continue;
			}
//            int goalType = goal.getType();
//            MissionGoalTypeLogic goalTypeLogic = getGoalTypeLogic(goalType);
//            if (goalTypeLogic == null) {
//                allGoalComplete = false;
//                continue;
//            }
//            int goalValue = getGoalValue(missionConfig, i);
//            int[] goalArgs = getGoalArgs(missionConfig, i);
//            change |= goalTypeLogic.refreshMissionGoal(player, goal, goalValue, goalArgs);
//            if (goal.getStatus() != MissionStatus.COMPLETE) {
//                allGoalComplete = false;
//            }
		}
		if (allGoalComplete) {
//            completeMission(player, mission);
		}
		return change;
	}

	/**
	 * 获取任务的数据
	 * 
	 * @param playerId       玩家id
	 * @param createIfAbsent 不存在是否创建
	 */
	public abstract MissionTypeData getMissionTypeData(long playerId, boolean createIfAbsent);

	@Override
	public void abort(long playerId, int questId) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleEvent(long playerId, Mission mission, IEvent event) {
		if (mission.getState() != MissionState.STATE_NONE.getValue()) {
			return false;
		}
		List<MissionGoal> goals = mission.getGoals();
		if (goals == null || goals.isEmpty()) {
			// 任务无目标 直接视为完成
			return completeMission(playerId, mission);
		}
		int missionId = mission.getConfigId();
		T missionConfig = this.getConfig(missionId);
		if (missionConfig == null) {
			return false;
		}
		boolean change = false;
		boolean allGoalComplete = true;
		String eventId = event.getEventId();
		for (int i = 0; i < goals.size(); i++) {
			MissionGoal goal = goals.get(i);
			if (goal.getStatus() == MissionState.STATE_COMPLETE.getValue()) {
				continue;
			}
			int goalType = goal.getType();
			IMissionGoalType goalTypeLogic = goalTypeManager.getGoalType(goalType);
			if (goalTypeLogic == null) {
				allGoalComplete = false;
				continue;
			}
			if (StringUtils.containsAny(eventId, goalTypeLogic.focusEvents())) {
				allGoalComplete = false;
				continue;
			}
			int completeCondition = missionConfig.getCompleteCondition()[i];
			int completeValue = missionConfig.getCompleteValue()[i];
			change |= goalTypeLogic.process(playerId, event, goal, completeCondition, completeValue);
			if (goal.getStatus() != MissionState.STATE_COMPLETE.getValue()) {
				allGoalComplete = false;
			}
		}
		if (allGoalComplete) {
			completeMission(playerId, mission);
		}
		return change;
	}

	/**
	 * 完成任务, 所有任务目标均为完成状态, 任务才算完成
	 * 
	 * @param playerId 玩家id
	 * @param mission  任务对象
	 */
	protected boolean completeMission(long playerId, Mission mission) {
		mission.setState((byte) MissionState.STATE_COMPLETE.getValue());
		this.afterCompleteMission(playerId, mission);
		return true;
	}

	/**
	 * 完成后的一些逻辑业务, 比如说, 如果任务是自动提交领取奖励, 则直接处理
	 */
	protected void afterCompleteMission(long playerId, Mission mission) {
		T missionConfig = this.getConfig(mission.getConfigId());
		if (missionConfig.autoSubmit()) {
			// 如果自动提交任务, 则帮玩家领取任务奖励
			this.submit(playerId, mission);
		}
	}

	@Override
	public ResultCodeData<Map<Integer, Integer>> submit(long playerId, Mission mission) {
		ErrorCode resultCode = checkSubmit(playerId, mission);
		if (!resultCode.isSuccess()) {
			return ResultCodeData.of(resultCode);
		}
		// 1.发送奖励
		Map<Integer, Integer> rewardMap = this.receiveReward(playerId, mission);
		// 2.处理提交任务
		this.doSubmitMission(playerId, mission);
		// 3.处理提交任务后的操作,如记录日志
		this.afterSubmitMission(playerId, mission);
		// 4.通知玩家
		this.tellMissionUpdate(playerId, mission);
		// 5.奖励返回上层
		return ResultCodeData.of(rewardMap);
	}

	/**
	 * 领取任务奖励
	 * 
	 * @param playerId 玩家id
	 * @param mission  任务对象
	 * @return 奖励map, 返回的引用, 禁止对其内容进行修改.避免造成灾难性的后果
	 */
	protected Map<Integer, Integer> receiveReward(long playerId, Mission mission) {
		if (mission == null) {
			return Collections.emptyMap();
		}
		T config = this.getConfig(mission.getConfigId());
		if (config == null) {
			return Collections.emptyMap();
		}
		resourceGroupService.reward(playerId, config.getMissionReward(), NatureEnum.MISSION_REWARD);
		return config.getMissionReward();
	}

	/**
	 * 判断是否可以提交任务
	 * 
	 * @param playerId 玩家id
	 * @param mission  任务对象
	 * @return 错误码
	 */
	protected ErrorCode checkSubmit(long playerId, Mission mission) {
		long now = TimeUtil.now();
		this.checkAndRefresh(playerId, now, false);
		if (mission.getState() == MissionState.STATE_REWARDED.getValue()) {
			return ErrorCode.MISSION_REWARDES;
		}
		if (mission.getState() != MissionState.STATE_COMPLETE.getValue()) {
			refreshMission(playerId, mission);
			if (mission.getState() != MissionState.STATE_COMPLETE.getValue()) {
				return ErrorCode.MISSION_NOT_COMPLATE;
			}
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 处理提交任务
	 * @param playerId
	 * @param mission
	 */
	protected void doSubmitMission(long playerId, Mission mission) {
		MissionTypeData sourceTypeData = this.getMissionTypeData(playerId, true);
		// 移除任务
		mission.setState((byte) MissionState.STATE_REWARDED.getValue());
		int missionId = mission.getConfigId();
		sourceTypeData.getMissions().remove(missionId);
		// 记录完成的任务id
		sourceTypeData.addFinishId(missionId);
		sourceTypeData.setLastTime(TimeUtil.now());
	}

	/**
	 * 处理提交任务后的操作
	 * @param playerId 玩家id 
	 * @param mission 任务对象
	 */
	protected abstract void afterSubmitMission(long playerId, Mission mission);

	/**
	 * 通知玩家任务更新
	 * 
	 * @param playerId 玩家id
	 * @param mission  任务对象
	 */
	protected void tellMissionUpdate(long playerId, Mission mission) {
		if (mission == null) {
			return;
		}
		RespMissionInfoBuilder builder = RespMissionInfoBuilder.newInstance();
		builder.addMissionInfos(mission.toProto());
		playerService.sendMessage(playerId, builder);
	}

}
