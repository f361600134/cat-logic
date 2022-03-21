package com.cat.server.game.module.mission2.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 任务类型数据, 对任务进行封装
 * @author Jeremy
 * @date 2021年2月11日下午4:06:54
 */
public class MissionTypeData {

	/**
	 * 当前已接取具体任务类, 领取奖励以后从任务列表中移除, 
	 * 如无特殊情况, 不允许初始化时加载玩家所有任务数据,
	 */
	private Map<Integer, Mission> missions = new HashMap<>();

	/**
	 * 已完成任务Id列表, 对已经完成的任务, 在此记录
	 */
	private List<Integer> finishIds = new ArrayList<>();
	
	/**
	 * 最后操作时间
	 */
	private long lastTime;

	public MissionTypeData() {
		this.missions = Maps.newHashMap();
		this.finishIds = Lists.newArrayList();
	}

	public Map<Integer, Mission> getMissions() {
		return missions;
	}

	public void setMissionPojos(Map<Integer, Mission> missions) {
		this.missions = missions;
	}
	
	public void addMissionPojo(Mission mission) {
		this.missions.put(mission.getConfigId(), mission);
	}
	
	public List<Integer> getFinishIds() {
		return finishIds;
	}

	public void setFinishIds(List<Integer> finishIds) {
		this.finishIds = finishIds;
	}
	
	public Mission getMission(int configId) {
		return missions.get(configId);
	}
	
	/**
	 * 数据清理
	 */
	public void clear() {
		this.finishIds.clear();
		this.missions.clear();
	}
	
	/**
	 * 任务是否完成/是否已领奖
	 * @param configId
	 */
	public boolean isFinished(int configId) {
		return finishIds.contains(configId);
	}
	
	
//	/**
//	 * 当完成任务, 
//	 * 已完成任务Id列表加入次任务, 当前已接取任务移除掉该任务
//	 */
//	public void onFinished(int configId) {
//		Mission t = getMission(configId);
//		if (t == null) {//FIXME 无该任务,继续移除?
//			return;
//		}
//		if (t.isRewarded()) {
//			finishIds.add(configId);
//			missionPojos.remove(configId);
//		}
//	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	@Override
	public String toString() {
		return "MissionTypeData [missions=" + missions + ", finishIds=" + finishIds + "]";
	}

}
