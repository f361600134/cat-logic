package com.cat.server.game.data.config.local;

import java.util.concurrent.TimeUnit;
import com.alibaba.fastjson.annotation.JSONField;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigActivityScheduleTimeBase;
import com.cat.server.game.data.config.local.interfaces.IConfigActivitySchedule;
import com.cat.server.game.module.activity.time.parse.TimePointParser;
import com.cat.server.game.module.activity.time.point.ITimePoint;


/**
 * hd.活动时间表.xlsx<br>
 * activity_schedule_time.json<br>
 * 
 * @author auto gen
 *
 */
@ConfigPath("activity_schedule_time.json")
public class ConfigActivityScheduleTime extends ConfigActivityScheduleTimeBase implements IConfigActivitySchedule{
	
	@JSONField(name="startTime", deserializeUsing = TimePointParser.class)
	private ITimePoint startTimePoint;

	public ITimePoint getStartTimePoint() {
		return startTimePoint;
	}
	
	public void setStartTimePoint(ITimePoint startTimePoint) {
		this.startTimePoint = startTimePoint;
	}
	
	/**
	 * 活动开始,就进入准备阶段,所以准备阶段的时间,等同于startTime时间点
	 * @param now
	 * @date 2022年10月15日下午7:34:34
	 */
	public long getPrepareTimestamp(long now) {
		// 活动相关时间
        ITimePoint startTime = this.getStartTimePoint();
        long startTimestamp = startTime.getLastTime(now);
        return startTimestamp;
	}
	
	/**
	 * 获取活动正式开始的时间点,即为准备阶段结束时
	 * @param now
	 * @date 2022年10月15日下午7:34:34
	 */
	public long getBeginTimestamp(long now) {
		return this.getPrepareTimestamp(now) + TimeUnit.SECONDS.toMillis(this.getPrepareDuration());
	}
	
	/**
	 * 获取活动结算时间点,即为开始阶段结束时
	 * @param now
	 * @date 2022年10月15日下午7:34:34
	 */
	public long getSettleTimestamp(long now) {
		return this.getBeginTimestamp(now) + TimeUnit.SECONDS.toMillis(this.getBeginDuration());
	}
	
	/**
	 * 获取活动结束时间点,即为结算阶段结束时
	 * @param now
	 * @date 2022年10月15日下午7:34:34
	 */
	public long getCloseTimestamp(long now) {
		return this.getSettleTimestamp(now) + TimeUnit.SECONDS.toMillis(this.getSettleTimestamp(now));
	}

}
