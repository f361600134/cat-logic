package com.cat.server.game.module.mission2.type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	private Set<Integer> finishIds = new HashSet<>();
	
	/**
	 * 最后操作时间
	 */
	private long lastTime;

	public MissionTypeData() {
	}

//	public Map<Integer, Mission> getMissions() {
//		return missions;
//	}

	public void setMissions(Map<Integer, Mission> missions) {
		this.missions = missions;
	}

	public Mission getMission(int configId) {
		return missions.get(configId);
	}
	
	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
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
	
	/**
	 * 添加完成任务
	 * @param configId
	 */
	public void addFinishId(int configId) {
		this.finishIds.add(configId);
	}
	
	/**
	 * 移除掉已完成的任务id
	 * @param configId 任务id
	 */
	public void removeFinishId(int configId) {
		this.finishIds.remove(configId);
	}
	
	/**
	 * 移除掉所有的已完成任务
	 * @param configId 任务id
	 */
	public void removeAllFinish() {
		this.finishIds.clear();
	}

	/**
	 * 添加新任务
	 */
	public void addMission(Mission mission) {
		this.missions.put(mission.getConfigId(), mission);
	}
	
	/**
	 * 是否存在指定任务
	 */
	public boolean hasMission(int configId) {
		return this.missions.containsKey(configId);
	}
	
	/**
	 * 移除掉指定任务
	 */
	public Mission removeMission(int configId) {
		return this.missions.remove(configId);
	}
	
	@Override
	public String toString() {
		return "MissionTypeData [missions=" + missions + ", finishIds=" + finishIds + "]";
	}
	
}
