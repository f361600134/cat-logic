package com.cat.server.game.module.mission.domain;

import com.cat.server.core.annotation.NotUse;
import com.cat.server.game.module.mission.define.QuestState;
import com.cat.server.game.module.mission.proto.PBMissionQuestGoalBuilder;

/**
 * 	任务目标
 * @author Jeremy
 */
@NotUse
public class QuestGoal {
	
	/**
	 * 目标对应下标
	 */
	private int index;
	/**
	 * 目标类型
	 * 
	 * {@link PlayerTargetType}
	 */
	private int type;

	/**
	 * 当前进度
	 */
	private long progress;
	
	/**
	 * 额外参数,部分任务会用上
	 */
	protected long additional;
	
	/**
	 * 状态<br>
	 * 
	 * {@link QuestState}
	 */
	private byte state;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getProgress() {
		return progress;
	}

	public void setProgress(long progress) {
		this.progress = progress;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}
	
	public void setAdditional(long additional) {
		this.additional = additional;
	}
	
	public long getAdditional() {
		return additional;
	}

	/**
	 * 任务目标转任务协议对象
	 * @return  
	 * @date 2022年3月19日下午10:52:55
	 */
	public PBMissionQuestGoalBuilder toProto() {
		PBMissionQuestGoalBuilder builder = PBMissionQuestGoalBuilder.newInstance();
		builder.setIndex(this.getIndex());
		builder.setProgress(this.getProgress());
		builder.setState(this.getState());
		builder.setType(this.getType());
		return builder;
	}
	
}
