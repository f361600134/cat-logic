package com.cat.server.game.module.pet.domain;

/**
 * 宠物技能
 * @author Jeremy
 */
public class PetSkill {
	
	/**
	 * 宠物技能id
	 */
	private int configId;
	/**
	 * 技能等级
	 */
	private int level;
	/**
	 * 领悟技能时间
	 */
	private long comprehendTime;

	protected int getConfigId() {
		return configId;
	}

	protected void setConfigId(int configId) {
		this.configId = configId;
	}

	protected int getLevel() {
		return level;
	}

	protected void setLevel(int level) {
		this.level = level;
	}

	protected long getComprehendTime() {
		return comprehendTime;
	}

	protected void setComprehendTime(long comprehendTime) {
		this.comprehendTime = comprehendTime;
	}
	
	
	
}
