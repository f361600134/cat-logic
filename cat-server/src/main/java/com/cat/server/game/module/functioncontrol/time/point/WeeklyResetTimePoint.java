package com.cat.server.game.module.functioncontrol.time.point;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.cat.server.utils.TimeUtil;

/**
 * 每周重置, 支持同一天多时间点重置
 * @author Jeremy
 */
public class WeeklyResetTimePoint extends DailyResetTimePoint implements IResetTimePoint{
	
	/**
	 * 需要重置的天
	 */
	protected List<Integer> dayOfWeeks;
	
	protected List<Integer> getDayOfWeeks() {
		return dayOfWeeks;
	}

	protected void setDayOfWeeks(List<Integer> dayOfWeeks) {
		this.dayOfWeeks = dayOfWeeks;
	}
	
	public WeeklyResetTimePoint() {}
	
	public WeeklyResetTimePoint(List<Integer> dayOfWeeks, List<Integer> hours) {
		this.dayOfWeeks = dayOfWeeks;
		this.hours = hours;
	}

	@Override
	public long getResetTimePoint(long now) {
		if (CollectionUtils.isEmpty(dayOfWeeks) && CollectionUtils.isEmpty(hours)) {
			return RESET_FREE;
		}
		Calendar car = Calendar.getInstance();
		//当前天是第几天
		int curDay = car.get(Calendar.DAY_OF_WEEK) -1;
		boolean bool = CollectionUtils.containsAny(dayOfWeeks, curDay);
		if (!bool) {
			return RESET_FREE;
		}
		//组装LocalDate
		int curYear = car.get(Calendar.YEAR);
		int curMonth = car.get(Calendar.MONTH)+1;
		int curDayOfMonth = car.get(Calendar.DAY_OF_MONTH);
		LocalDate localDate = LocalDate.of(curYear, curMonth, curDayOfMonth);
		
		//当前时
		int curHour = car.get(Calendar.HOUR_OF_DAY);
		if (CollectionUtils.isEmpty(hours)) {
			curHour = 0;
		}else {
			for (int i = hours.size()-1; i >= 0; i--) {
				int hour = hours.get(i);
				if (curHour >= hour) {
					curHour = hour;
					break;
				}
			}
		}
		LocalTime localTIme = LocalTime.of(curHour, 0, 0, 0);
		return TimeUtil.getTimestamp(localDate, localTIme);
	}
	
}
