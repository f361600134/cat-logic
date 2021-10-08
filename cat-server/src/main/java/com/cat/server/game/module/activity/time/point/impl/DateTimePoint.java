package com.cat.server.game.module.activity.time.point.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.cat.server.game.module.activity.time.point.AbstractUniqueTimePoint;

/**
 * 指定了日期时间点, 精确到小时.
 * @author Jeremy
 */
public class DateTimePoint extends AbstractUniqueTimePoint{
	
	private final int year;

    private final int month;

    private final int day;

    private final int hour;

	public DateTimePoint(int year, int month, int day, int hour) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		 // 计算该时间点
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime dateTime = ZonedDateTime.of(year, month, day, hour, 0, 0, 0, zoneId);
        this.uniqueTime = dateTime.toInstant().toEpochMilli();
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

}
