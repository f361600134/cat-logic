package com.cat.server.game.module.activity.time.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUniqueTimePoint implements ITimePoint {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 时间点的时间戳,一个表达式不同时刻的时间戳不一样
	 */
	protected long uniqueTime;
	
	public AbstractUniqueTimePoint() {
	}

	public AbstractUniqueTimePoint(long uniqueTime) {
		this.uniqueTime = uniqueTime;
	}

	public long getUniqueTime() {
		return uniqueTime;
	}

	public void setUniqueTime(long uniqueTime) {
		this.uniqueTime = uniqueTime;
	}
	
}
