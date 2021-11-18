package com.cat.server.game.module.recycle.strategy.impl;

import java.util.concurrent.TimeUnit;

import com.cat.server.game.module.recycle.strategy.IRecycleStrategy;

public class DateRecycleStrategy implements IRecycleStrategy {

	private final int day;
	private final int hour;
	
	public DateRecycleStrategy(int day, int hour) {
		super();
		this.day = day;
		this.hour = hour;
	}
	
	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	/**
	 * 根据日期计算结束时间戳
	 */
	@Override
	public long calculateTimePoint(long time) {
		long dayMails = TimeUnit.DAYS.toMillis(day);
		long hourMails = TimeUnit.HOURS.toMillis(hour);
		return time+dayMails+hourMails;
	}

}
