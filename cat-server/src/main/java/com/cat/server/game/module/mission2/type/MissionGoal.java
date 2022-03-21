package com.cat.server.game.module.mission2.type;

import com.cat.server.core.annotation.NotUse;
import com.cat.server.game.module.mission2.domain.MissionState;
import com.cat.server.game.module.mission2.proto.PBMissionGoalBuilder;

/**
 * 	任务目标
 * @author Jeremy
 */
@NotUse
public class MissionGoal {
	
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
	 * {@link MissionState}
	 */
	private int status;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	public PBMissionGoalBuilder toProto() {
		PBMissionGoalBuilder builder = PBMissionGoalBuilder.newInstance();
		builder.setIndex(this.getIndex());
		builder.setProgress(this.getProgress());
		builder.setState(this.getStatus());
		builder.setType(this.getType());
		return builder;
	}
	
//	/**
//	 * 创建任务目标协议
//	 * 
//	 * @return
//	 */
//	public MissionGoalBean buildProto() {
//		MissionGoalBean.Builder builder = MissionGoalBean.newBuilder();
//		builder.setIndex(index);
//		builder.setType(type);
//		builder.setProgress(progress);
//		builder.setStatus(status);
//		return builder.build();
//	}
	
}
