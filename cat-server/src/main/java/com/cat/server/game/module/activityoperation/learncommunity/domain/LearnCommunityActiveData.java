package com.cat.server.game.module.activityoperation.learncommunity.domain;

/**
 * 研习社日活跃信息
 * @author Jeremy
 * @date 2020-12-15 16:31:28
 */
public class LearnCommunityActiveData {

	/**
	 * 配置id
	 */
	private int configId;
	/**
	 * 活跃奖励是否已领取
	 */
	private boolean rewarded;

	public LearnCommunityActiveData() {
	}

	public LearnCommunityActiveData(int configId) {
		this.configId = configId;
	}

	/** 配置id **/
	public int getConfigId() {
		return this.configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public boolean isRewarded() {
		return rewarded;
	}

	public void setRewarded(boolean rewarded) {
		this.rewarded = rewarded;
	}

}
