package com.cat.server.core.lifecycle;

/**
 * 优先级由小到大
 *
 */
public enum Priority {
	
	/** 最高优先级 */
	HIGHEST(Integer.MIN_VALUE),
	
	/** 系统环境初始化,网络注册,数据库连接池初始化, spring脚手架扫描等系统环境级别优先级 */
	SYSTEM_INITIALIZATION(0),

	/** 业务环境级,事件注册,策划配置初始化*/
	LOGIC_INITIALIZATION(100),
	
	/** 业务级别, 业务内公共模块数据初始化, 纯业务级别的顺序*/
	LOGIC(200),
	
	/** 默认级别*/
	DEFAULT(300),
	
	/** 优先级最低  */
	LOWEST(Integer.MAX_VALUE),
	;
	
	
	private int priority;
	
	private Priority(int priority){
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
	
}
