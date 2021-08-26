package com.cat.robot.common.lifecycle;

/**
 * 优先级由小到大
 *
 */
public enum Priority {

	/** 最高优先级 */
	HIGHEST(Integer.MIN_VALUE),

	/** 系统环境初始化最先执行 */
	SYSTEM_INITIALIZATION(0),

	/** 业务环境级 */
	LOGIC_INITIALIZATION(100),

	/** 业务级别 */
	LOGIC(200),

	/** 优先级最低 */
	LOWEST(Integer.MAX_VALUE),;

	private int priority;

	private Priority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

}
