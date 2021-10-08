package com.cat.server.game.module.activity.time.point.impl;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.module.activity.time.point.AbstractMultiTimePoint;
import com.cat.server.game.module.activity.time.point.ITimePoint;
import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;

/**
 * 开服时间的时间点
 * @author Jeremy
 */
public class OpenDayTimePoint extends AbstractMultiTimePoint implements ITimePoint{

	/**
	 * 开服第几天<br>
	 * 开服当天为第1天
	 */
	private final int openDay;
	/**
	 * 时
	 */
	private final int hour;
	/**
	 * 上次计算时的开服时间戳
	 */
	private long oldOpenTimestamp;
	
	public OpenDayTimePoint(int openDay, int hour) {
		this.openDay = openDay;
		this.hour = hour;
	}
	
	@Override
    public long getUniqueTime() {
        checkAndRefresh(TimeUtil.now());
        return lastTime;
    }

	@Override
	protected Pair<Long, Long> calculateTime(long time) {
		ServerConfig serverConfig = SpringContextHolder.getBean(ServerConfig.class);
		LocalDate openDate = serverConfig.getOpenDate();
		LocalDate localDate = openDate.plusDays(openDay - 1);
		LocalTime localTime = LocalTime.of(hour, 0, 0, 0);
		long uniqueTime = TimeUtil.getTimestamp(localDate, localTime);
		this.oldOpenTimestamp = TimeUtil.getTimestamp(openDate);
		Pair<Long, Long> timePair = new Pair<>(uniqueTime, uniqueTime);
		return timePair;
	}

	@Override
	protected boolean needCalculateTime(long time) {
		if (oldOpenTimestamp == 0L) {
			return true;
		}
		ServerConfig serverConfig = SpringContextHolder.getBean(ServerConfig.class);
		long openTimestamp = TimeUtil.getTimestamp(serverConfig.getOpenDate());
		if (openTimestamp != oldOpenTimestamp) {
			return true;
		}
		return false;
	}

}
