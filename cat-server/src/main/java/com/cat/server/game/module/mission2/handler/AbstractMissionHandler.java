package com.cat.server.game.module.mission2.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.GameEventBus;
import com.cat.server.core.event.IEvent;
import com.cat.server.core.event.PlayerBaseEvent;
import com.cat.server.game.data.config.local.ext.IConfigMission;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.mission2.domain.MissionState;
import com.cat.server.game.module.mission2.type.Mission;
import com.cat.server.game.module.mission2.type.MissionGoal;
import com.cat.server.game.module.mission2.type.MissionTypeData;

public abstract class AbstractMissionHandler<T extends IConfigMission> implements IMissionHandler<T>{
	
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
	 * @param playerId 玩家id
	 * @param missionId 任务配置id
	 * @param now 当前时间
	 * @return ErrorCode  
	 */
	protected ErrorCode checkAccept(long playerId, int missionId, long now) {
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 构建任务对象
	 * @return  
	 * @return Mission  
	 * @date 2022年3月19日下午7:06:08
	 */
	protected Mission buildMission(T config) {
		//初始化任務目標
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
		mission.setSourceType(this.getMissionType().getType());
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
	 * @param playerId 玩家id
	 * @param createIfAbsent 不存在是否创建
	 */
	public abstract MissionTypeData getMissionTypeData(long playerId, boolean createIfAbsent);
	
	
	@Override
	public ResultCodeData<Map<Integer, Integer>> submit(long playerId, int questId) {
		// TODO Auto-generated method stub
		return null;
	}

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
        	return  completeMission(playerId, mission);
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
            MissionGoalTypeLogic goalTypeLogic = getGoalTypeLogic(goalType);
            if (goalTypeLogic == null) {
                allGoalComplete = false;
                continue;
            }
            if (!StringUtils.equals(eventId, goalTypeLogic.listenEventId())) {
                // 该事件与该类型无关
                allGoalComplete = false;
                continue;
            }
            int goalValue = getGoalValue(missionConfig, i);
            int[] goalArgs = getGoalArgs(missionConfig, i);
            change |= goalTypeLogic.refreshMissionGoal(player, event, goal, goalValue, goalArgs);
            if (goal.getStatus() != MissionStatus.COMPLETE) {
                allGoalComplete = false;
            }
        }
        if (allGoalComplete) {
            completeMission(player, mission);
        }
        return change && needShow();
	}
	
	/**
	 * 完成任务
	 * @param playerId 玩家id
	 * @param mission 任务对象
	 */
	protected boolean completeMission(long playerId, Mission mission) {
		
		return false;
	}
	
}
