package com.cat.server.game.module.mission.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.config.local.ext.IConfigMission;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.mission.MissionService;
import com.cat.server.game.module.mission.define.QuestState;
import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.domain.QuestGoal;
import com.cat.server.game.module.mission.domain.QuestTypeData;
import com.cat.server.game.module.mission.goal.IQuestGoalType;
import com.cat.server.game.module.mission.manager.MissionManager;
import com.cat.server.game.module.mission.proto.PBMissionQuestInfoBuilder;
import com.cat.server.game.module.mission.proto.RespMissionInfoBuilder;
import com.cat.server.game.module.mission.proto.RespMissionSingleQuestBuilder;
import com.cat.server.game.module.mission.reset.IQuestReset;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.utils.TimeUtil;

public abstract class AbstractMissionHandler<T extends IConfigMission> implements IQuestHandler<T> {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired protected MissionManager missionManager;
	@Autowired protected IPlayerService playerService;
	@Autowired protected IResourceGroupService resourceGroupService;
	
	@Autowired protected MissionService missionService;

	protected Class<T> missionConfigClazz;

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
	public void checkAndReset(long playerId, long now, boolean notify) {
		if (!isNeedReset(playerId, now)) {
            return;
        }
        this.reset(playerId, now, notify);
	}
	
	/**
	 * 判断是否需要重置
	 * @param playerId 玩家id
	 * @param now 
	 * @return
	 */
	protected boolean isNeedReset(long playerId, long now) {
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, false);
        if (questTypeData == null) {
            return false;
        }
        long lastTime = questTypeData.getLastTime();
        return lastTime <= 0 || !TimeUtil.isSameDay(lastTime, now);
	}
	
	/**
	 * 任务刷新操作<br>
	 * 如果有这个任务, 且已完成, 则移除掉次任务, 重新接取<br>
	 * 如果没有这个任务, 则直接重新接取
	 * @param playerId 玩家id
	 * @param now 时间戳
	 * @param notify 是否通知玩家更新
	 */
	protected void reset(long playerId, long now, boolean notify) {
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, true);
		questTypeData.setLastTime(now);
		Map<Integer, T> configs = this.getConfigs();
		for (T config : configs.values()) {
			final int configId = config.getId();
			Quest quest = questTypeData.getQuest(configId);
			if (quest == null) {
				//无此任务,可能已经完成,也可能是策划新增任务
				//从已完成列表尝试移除, 然后直接帮玩家接这个任务
				this.clear(playerId, configId);
				this.accept(playerId, configId, now);
				continue;
			}
			//有此任务, 则判断是否符合重置条件, 每日重置/每周重置,符合则重新接取
			IQuestReset questResetType = missionManager.getQuestReset(config.getResetType());
			if (questResetType == null) {
				//不存在刷新配置,則记录日志,跳过重置
				log.info("reset error, Invalid type:[{}]", config.getResetType());
				continue;
			}
			questResetType.checkAndReset(playerId, quest, this);
		}
	}

	@Override
	public ResultCodeData<Quest> accept(long playerId, int configId, long now) {
		T missionConfig = this.getConfig(configId);
		if (missionConfig == null) {
			return ResultCodeData.of(ErrorCode.CONFIG_NOT_EXISTS);
		}
		ErrorCode resultCode = this.checkAccept(playerId, configId, now);
		if (resultCode != null) {
			return ResultCodeData.of(resultCode);
		}
		Quest quest = buildMission(missionConfig);
		if (quest == null) {
			return ResultCodeData.of(ErrorCode.CONFIG_NOT_EXISTS);
		}
		// 领取任务
		this.doAcceptQuest(playerId, quest, now);
		return ResultCodeData.of(quest);
	}

	/**
	 * 检测是否可以接任务
	 * @param playerId  玩家id
	 * @param configId 任务配置id
	 * @param now       当前时间
	 * @return ErrorCode
	 */
	protected ErrorCode checkAccept(long playerId, int configId, long now) {
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, false);
        if (questTypeData == null) {
            return null;
        }
        this.checkAndReset(playerId, now, false);
        if (questTypeData.hasMission(configId)) {
        	return ErrorCode.MISSION_WAS_ACCEPTED;
		}
        // 判断是否完成过任务
        if (questTypeData.isFinished(configId)) {
            return ErrorCode.MISSION_REWARDES;
        }
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 构建任务对象
	 * @return
	 * @return Mission
	 * @date 2022年3月19日下午7:06:08
	 */
	protected Quest buildMission(T config) {
		// 初始化任務目標
		int[] completeTypes = config.getCompleteType();
		List<QuestGoal> goalList = new ArrayList<>();
		for (int i = 0; i < completeTypes.length; i++) {
			int completeType = completeTypes[i];
			QuestGoal goal = new QuestGoal();
			goal.setIndex(0);
			goal.setType(completeType);
			goal.setState(QuestState.STATE_NONE.getValue());
			goalList.add(goal);
		}
		Quest mission = new Quest(config.getId());
		mission.setSourceType((short) this.getMissionType().getType());
		mission.setGoals(goalList);
		return mission;
	}

	/**
	 * 处理接取任务
	 * @param playerId
	 * @param mission
	 * @param now
	 */
	protected void doAcceptQuest(long playerId, Quest mission, long now) {
		// 记录
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, true);
		questTypeData.addMission(mission);
		questTypeData.setLastTime(now);
		// 尝试刷新任务状态
		refreshQuest(playerId, mission);
	}

	@Override
	public boolean refreshQuest(long playerId, Quest mission) {
		if (mission.getState() != QuestState.STATE_NONE.getValue()) {
			return false;
		}
		List<QuestGoal> goals = mission.getGoals();
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
			QuestGoal goal = goals.get(i);
			if (goal.getState() == QuestState.STATE_COMPLETE.getValue()) {
				continue;
			}
            int goalType = goal.getType();
            IQuestGoalType goalTypeLogic = missionManager.getGoalType(goalType);
            if (goalTypeLogic == null) {
                allGoalComplete = false;
                continue;
            }
            int completeCondition = missionConfig.getCompleteCondition()[i];
			int completeValue = missionConfig.getCompleteValue()[i];
            change |= goalTypeLogic.refresh(playerId, goal, completeCondition, completeValue);
            if (goal.getState() != QuestState.STATE_COMPLETE.getValue()) {
                allGoalComplete = false;
            }
		}
		if (allGoalComplete) {
            completeQuest(playerId, mission);
		}
		return change;
	}

	@Override
	public void abort(long playerId, int missionId) {
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, false);
		if (questTypeData == null) {
            return;
        }
		// 移除任务
        Quest quest = questTypeData.removeMission(missionId);
        if (quest == null) {
            return;
        }
        // 任务设置为失败状态
        quest.setState(QuestState.STATE_FAILD.getValue());
        quest.setGoals(null);
	}
	
	@Override
	public void clear(long playerId, int missionId) {
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, false);
		if (questTypeData == null) {
            return;
        }
		questTypeData.removeFinishId(missionId);
	}

	@Override
	public boolean handleEvent(long playerId, Quest quest, IEvent event) {
		if (quest.getState() != QuestState.STATE_NONE.getValue()) {
			return false;
		}
		List<QuestGoal> goals = quest.getGoals();
		if (goals == null || goals.isEmpty()) {
			// 任务无目标 直接视为完成
			return completeQuest(playerId, quest);
		}
		int configId = quest.getConfigId();
		T missionConfig = this.getConfig(configId);
		if (missionConfig == null) {
			return false;
		}
		
		boolean change = false;
		boolean allGoalComplete = true;
		String eventId = event.getEventId();
		for (int i = 0; i < goals.size(); i++) {
			QuestGoal goal = goals.get(i);
			if (goal.getState() == QuestState.STATE_COMPLETE.getValue()) {
				continue;
			}
			int goalType = goal.getType();
			IQuestGoalType goalTypeLogic = missionManager.getGoalType(goalType);
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
			if (goal.getState() != QuestState.STATE_COMPLETE.getValue()) {
				allGoalComplete = false;
			}
		}
		if (allGoalComplete) {
			completeQuest(playerId, quest);
		}
		return change;
	}

	/**
	 * 完成任务, 所有任务目标均为完成状态, 任务才算完成
	 * 
	 * @param playerId 玩家id
	 * @param mission  任务对象
	 */
	protected boolean completeQuest(long playerId, Quest mission) {
		mission.setState(QuestState.STATE_COMPLETE.getValue());
		this.afterComplete(playerId, mission);
		return true;
	}

	/**
	 * 完成后的一些逻辑业务, 比如说, 如果任务是自动提交领取奖励, 则直接处理
	 */
	protected void afterComplete(long playerId, Quest quest) {
		T missionConfig = this.getConfig(quest.getConfigId());
		if (missionConfig.autoSubmit()) {
			// 如果自动提交任务, 则帮玩家领取任务奖励
			this.submit(playerId, quest);
		}
	}

	@Override
	public ResultCodeData<Map<Integer, Integer>> submit(long playerId, Quest quest) {
		ErrorCode resultCode = checkSubmit(playerId, quest);
		if (!resultCode.isSuccess()) {
			return ResultCodeData.of(resultCode);
		}
		// 1.发送奖励
		Map<Integer, Integer> rewardMap = this.receiveReward(playerId, quest);
		// 2.处理提交任务
		this.doSubmitMission(playerId, quest);
		// 3.处理提交任务后的操作,如记录日志
		this.afterSubmitMission(playerId, quest);
		// 4.通知玩家
		this.tellMissionUpdate(playerId, quest);
		// 5.奖励返回上层
		return ResultCodeData.of(rewardMap);
	}

	/**
	 * 领取任务奖励
	 * @param playerId 玩家id
	 * @param quest  任务对象
	 * @return 奖励map, 返回的引用, 禁止对其内容进行修改.避免造成灾难性的后果
	 */
	protected Map<Integer, Integer> receiveReward(long playerId, Quest quest) {
		if (quest == null) {
			return Collections.emptyMap();
		}
		T config = this.getConfig(quest.getConfigId());
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
	 * @param quest  任务对象
	 * @return 错误码
	 */
	protected ErrorCode checkSubmit(long playerId, Quest quest) {
		long now = TimeUtil.now();
		this.checkAndReset(playerId, now, false);
		if (quest.getState() == QuestState.STATE_REWARDED.getValue()) {
			return ErrorCode.MISSION_REWARDES;
		}
		if (quest.getState() != QuestState.STATE_COMPLETE.getValue()) {
			refreshQuest(playerId, quest);
			if (quest.getState() != QuestState.STATE_COMPLETE.getValue()) {
				return ErrorCode.MISSION_NOT_COMPLATE;
			}
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 处理提交任务
	 * @param playerId
	 * @param quest
	 */
	protected void doSubmitMission(long playerId, Quest quest) {
		QuestTypeData sourceTypeData = this.getQuestTypeData(playerId, true);
		// 移除任务
		quest.setState(QuestState.STATE_REWARDED.getValue());
		int configId = quest.getConfigId();
		sourceTypeData.removeMission(configId);
		// 记录完成的任务id
		sourceTypeData.addFinishId(configId);
		sourceTypeData.setLastTime(TimeUtil.now());
	}

	/**
	 * 处理提交任务后的操作
	 * @param playerId 玩家id 
	 * @param quest 任务对象
	 */
	protected void afterSubmitMission(long playerId, Quest quest) {
		
	}

	/**
	 * 通知玩家任务更新
	 * @param playerId 玩家id
	 * @param quest  任务对象
	 */
	protected void tellMissionUpdate(long playerId, Quest quest) {
		if (quest == null) {
			return;
		}
		RespMissionSingleQuestBuilder builder = RespMissionSingleQuestBuilder.newInstance();
		builder.setQuest(quest.toProto().build());
		playerService.sendMessage(playerId, builder);
	}
	
	/**
	 * 获取任务的数据
	 * @param playerId       玩家id
	 * @param createIfAbsent 不存在是否创建
	 */
	public QuestTypeData getQuestTypeData(long playerId, boolean createIfAbsent) {
		return missionService.getQuestTypeData(playerId, this.getMissionType().getType(), createIfAbsent);
	}
	
	@Override
	public RespMissionInfoBuilder toProto(long playerId) {
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, true);
		RespMissionInfoBuilder missionBuilder = RespMissionInfoBuilder.newInstance();
		missionBuilder.setType(this.getMissionType().getType());
		for (IConfigMission config : this.getConfigs().values()) {
			int state = questTypeData.getState(config.getId());
			if (state == QuestState.STATE_NONE.getValue()) {
				//任务未完成,推送进度
				Quest quest = questTypeData.getQuest(config.getId());
				missionBuilder.addQuests(quest.toProto().build());
			}else {
				//任务已完成,推送状态即可
				PBMissionQuestInfoBuilder questBuilder = PBMissionQuestInfoBuilder.newInstance();
				questBuilder.setId(config.getId());
				questBuilder.setState(state);
				questBuilder.setType(this.getMissionType().getType());
				missionBuilder.addQuests(questBuilder.build());
			}
		}
		return missionBuilder;
	}

}
