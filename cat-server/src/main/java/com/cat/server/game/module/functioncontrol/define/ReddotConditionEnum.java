package com.cat.server.game.module.functioncontrol.define;

/**
 * 红点条件枚举类
 * 
 * @author Jeremy
 */
public enum ReddotConditionEnum {

	/** 主线任务 */
	MAIN_MISSION(1001),
	/** 每日任务 */
	DAILY_MISSION(1002),
	/** 研习社任务 */
	LEARN_COMMUNITY_MISSION(1003),
	/** 家族申请 */
	FAMILY_APPLY(1004),
	/** 新邮件 */
	MAIL(1005),
	
	;

	private int conditionId;

	private ReddotConditionEnum(int conditionId) {
		this.conditionId = conditionId;
	}

	/**
	 * 获取条件
	 * @return
	 */
	public int getConditionId() {
		return conditionId;
	}

}
