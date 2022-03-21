package com.cat.server.game.module.mission2.type;

import java.util.List;

import com.cat.server.game.data.proto.PBMission;
import com.cat.server.utils.TimeUtil;

/**
 * 默认任务对象
 * @auth Jeremy
 * @date 2022年3月19日下午7:07:57
 */
public class Mission {

	/**
	 * 任务ID
	 */
	protected int configId;
	/**
	 * 任务接取时间
	 */
	protected long recvTime;
	 /**
	  * 目标列表<br>
     */
    protected List<MissionGoal> goals;
    /**
     * 任务状态
     */
    protected byte state;
    /**
     * 任务来源，主线，支线，其他 
     */
    protected short sourceType;
	
	public Mission(int configId) {
		this.configId = configId;
		this.recvTime = TimeUtil.now();
	}
	
	/** 任务ID **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	/** 任务接取时间 **/
	public long getRecvTime(){
		return this.recvTime;
	}
	
	public void setRecvTime(long recvTime){
		this.recvTime = recvTime;
	}
	
	public List<MissionGoal> getGoals() {
		return goals;
	}

	public void setGoals(List<MissionGoal> goals) {
		this.goals = goals;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(short sourceType) {
		this.sourceType = sourceType;
	}

	public int getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}
	
	/**
	 * 转协议
	 * @return
	 */
	public PBMission.PBMissionInfo toProto() {
		PBMission.PBMissionInfo.Builder builder = PBMission.PBMissionInfo.newBuilder();
		builder.setId(getConfigId());
		builder.setType(getSourceType());
		//builder.setState(getState());
		for (MissionGoal goal : goals) {
			builder.addGoals(goal.toProto().build());
		}
		return builder.build();
	}
}
