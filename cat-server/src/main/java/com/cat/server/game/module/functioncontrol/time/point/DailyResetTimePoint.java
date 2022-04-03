package com.cat.server.game.module.functioncontrol.time.point;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import com.cat.server.utils.TimeUtil;

/**
 * 每日重置, 支持多时间点重置
 * @author Jeremy
 */
public class DailyResetTimePoint implements IResetTimePoint{
	
	/**
	 * 每日重置的小时整点
	 */
	protected List<Integer> hours;
	
	public DailyResetTimePoint() {}
	
	public DailyResetTimePoint(List<Integer> hours) {
		this.hours = hours;
	}
	
	
	protected List<Integer> getHours() {
		return hours;
	}

	protected void setHours(List<Integer> hours) {
		this.hours = hours;
	}
	

	@Override
	public long getResetTimePoint(long now) {
		if (CollectionUtils.isEmpty(hours)) {
			return RESET_FREE;
		}
		Calendar car = Calendar.getInstance();
		car.setTimeInMillis(now);
		int curHour = car.get(Calendar.HOUR_OF_DAY);
		for (int i = hours.size()-1; i >= 0; i--) {
			int hour = hours.get(i);
			if (curHour >= hour) {
				LocalDate localDate = LocalDate.now();
				//生成当前小时的时间戳
				LocalTime localTIme = LocalTime.of(hour, 0, 0, 0);
				return TimeUtil.getTimestamp(localDate, localTIme);
			}
		}
		//如果没有找到相关配置, 表示不需要重置, 返回一个负数
		return RESET_FREE;
	}
	
}
