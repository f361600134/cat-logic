package com.cat.server.game.module.mission.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.proto.PBBag;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mission.domain.MissionState;
import com.cat.server.game.module.mission.manager.MissionProcessManager;
import com.cat.server.game.module.mission.process.IMissionProcess;
import com.cat.server.game.module.mission.type.IMission;
import com.cat.server.game.module.mission.type.MissionTypeData;
import com.cat.server.game.module.resource.IResourceGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * 任务处理器, 每个任务模块维护自己的任务. 以及任务类型对应的id集合 注意:因为处理器代理处理任务逻辑, 任务内的所有状态变化,
 * 最后都需要显示的进行存储,
 * 
 * @author Jeremy
 */
public class MissionHandler<T extends IMission> implements IMissionHandler{

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 玩家id
	 */
	protected long playerId;

	/**
	 * 被代理的任务数据
	 */
	protected MissionTypeData<T> missionData;

	/**
	 * 任务奖励后监听器, 用于处理发奖之后操作
	 */
	protected IMissionListener<MissionTypeData<T>> afterRewardedListener;

	/**
	 * key:任务类型 value: 任务id集合
	 */
	private transient Multimap<Integer, Integer> missionConfigs;

	/**
	 * 已更新列表,存储有数据变动的任务信息
	 */
	private transient List<? super IMission> updateList;

	public MissionHandler(MissionTypeData<T> missionData) {
		this.missionConfigs = ArrayListMultimap.create();
		this.updateList = Lists.newArrayList();
		initMissionConfigs();
	}

//	public AbstractMissionHandler(long playerId, MissionTypeData<T> missionData) {
//		this.playerId = playerId;
//		this.missionData = missionData;
//		this.missionConfigs = ArrayListMultimap.create();
//		this.updateList = Lists.newArrayList();
//		initMissionConfigs();
//	}

//	public AbstractMissionHandler(Builder builder) {
//		this.playerId = builder.getPlayerId();
//		this.missionData = builder.getMissionData();
//		this.afterRewardedListener = builder.getAfterRewardedListener();
//		this.missionConfigs = ArrayListMultimap.create();
//		this.updateList = Lists.newArrayList();
//		this.initMissionConfigs();
//	}

	private void initMissionConfigs() {
		getMissions().forEach(e -> {
			this.missionConfigs.put(e.getCompleteType(), e.getConfigId());
		});
	}

	/**
	 * 
	 * @param <V>
	 * @param listener
	 * @return void
	 * @date 2021年2月28日下午10:24:28
	 */
	public MissionHandler<T> afterRewarded(IMissionListener<MissionTypeData<T>> listener) {
		this.afterRewardedListener = listener;
		return this;
	}

	public Collection<PBBag.PBMissionInfo> toProto() {
		List<PBBag.PBMissionInfo> colls = new ArrayList<PBBag.PBMissionInfo>();
		for (IMission mission : getMissions()) {
			colls.add(mission.toProto());
		}
		return colls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IMission> getUpdateList() {
		return (List<IMission>) updateList;
	}

	/**
	 * 处理任务 1.任务处理管理器通过任务类型获取到处理类, 2.通过任务类型获取到任务列表 3.当前任务集合,通过任务列表获取到任务对象,
	 * 被任务处理类去处理任务
	 * 
	 * @return
	 */
	public ErrorCode onProcess(IEvent event) {
		// TODO 这里不想每次都通过spring去获取manager, 看看怎么优化
		MissionProcessManager manager =  SpringContextHolder.getBean(MissionProcessManager.class);
		// 获取到任务类型
		Collection<Integer> missionTypes = manager.getMissionTypes(event.getEventId());
		for (Integer missionType : missionTypes) {
			IMissionProcess missionProcess = manager.getProcess(missionType);
			Collection<Integer> configIds = missionConfigs.get(missionType);
			for (Integer configId : configIds) {
				IMission mission = getMission(configId);
				boolean bool = missionProcess.process(mission, event);
				if (!bool)
					break;
				this.updateList.add(mission);
			}
		}
		return ErrorCode.SUCCESS;
	}

	/**
	 * 领取奖励, 具体奖励进背包由子类完成
	 */
	@Override
	public ErrorCode onReward(int configId, NatureEnum nenum) {
		IMission mission = getMission(configId);
		if (mission == null) {
			return ErrorCode.MISSION_NOT_EXIST; // 任务不存在
		}
		if (mission.isNone()) {
			return ErrorCode.MISSION_NOT_COMPLATE; // 任务未完成
		}
		if (mission.isRewarded()) {
			return ErrorCode.MISSION_REWARDES; // 任务已领奖
		}
		if (getMissionTypeData().isFinished(configId)) {
			return ErrorCode.MISSION_REWARDES; // 任务已领奖
		}
		// 处理奖励
		this.doReward(mission, nenum);
		// FIXME 处理领取任务是否需要分两步
		mission.setState(MissionState.STATE_REWARDED.getValue());
		missionData.onFinished(configId);
		// 加入更新列表
		this.updateList.add(mission);
		// 执行监听器
		this.afterRewardedListener.call(mission, missionData);
		return ErrorCode.SUCCESS;
	}

	/**
	 * 根据id获取任务
	 * 
	 * @param configId
	 * @return
	 */
	protected IMission getMission(int configId) {
		return getMissionTypeData().getMission(configId);
	}

	/**
	 * 获取任务集合
	 * 
	 * @return
	 */
	protected Collection<T> getMissions() {
		return getMissionTypeData().getMissionPojos().values();
	}

	public MissionTypeData<T> getMissionTypeData() {
		return missionData;
	}

	@Override
	public long getPlayerId() {
		return playerId;
	}

	/**
	 * 仅仅处理发奖
	 * 
	 * @param configId
	 */
	protected void doReward(IMission mission, NatureEnum nenum) {
		IResourceGroupService itemService =  SpringContextHolder.getBean(IResourceGroupService.class);
		itemService.reward(getPlayerId(), mission.getReward(), nenum);
	}

	public void setMissionData(MissionTypeData<T> missionData) {
		this.missionData = missionData;
	}
	
	public void setAfterRewardedListener(IMissionListener<MissionTypeData<T>> afterRewardedListener) {
		this.afterRewardedListener = afterRewardedListener;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
}
