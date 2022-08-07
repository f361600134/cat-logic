package com.cat.server.game.module.mission.domain;

import java.util.List;

import com.cat.server.game.module.mission.proto.PBMissionQuestInfoBuilder;
import com.cat.server.utils.TimeUtil;

/**
 * 默认任务对象
 * @auth Jeremy
 * @date 2022年3月19日下午7:07:57
 */
public class Quest {

	/**
	 * 任务ID
	 */
	protected int configId;
	/**
	 * 任务接取时间
	 */
	protected long acceptTime;
	 /**
	  * 目标列表<br>
     */
    protected List<QuestGoal> goals;
    /**
     * 任务状态,{@link #QuestState}
     */
    protected byte state;
    /**
     * 任务来源，主线，支线，其他 
     */
    protected short sourceType;
	
	public Quest(int configId) {
		this.configId = configId;
		this.acceptTime = TimeUtil.now();
	}
	
	/** 任务ID **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	public List<QuestGoal> getGoals() {
		return goals;
	}

	public long getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(long acceptTime) {
		this.acceptTime = acceptTime;
	}

	public void setGoals(List<QuestGoal> goals) {
		this.goals = goals;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(short sourceType) {
		this.sourceType = sourceType;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}
	
	/**
	 * 任务目标转任务协议对象
	 * @return  
	 * @date 2022年3月19日下午10:52:55
	 */
	public PBMissionQuestInfoBuilder toProto() {
		PBMissionQuestInfoBuilder builder = PBMissionQuestInfoBuilder.newInstance();
		builder.setId(this.getConfigId());
		builder.setType(this.getSourceType());
		builder.setState(this.getState());
		this.goals.forEach(goal->{
			builder.addGoals(goal.toProto().build());
		});
		return builder;
	}

}
