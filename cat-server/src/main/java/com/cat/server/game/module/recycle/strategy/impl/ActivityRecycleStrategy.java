package com.cat.server.game.module.recycle.strategy.impl;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.module.activity.IActivityService;
import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 依赖活动的回收策略
 * @author Jeremy
 *
 */
public class ActivityRecycleStrategy extends DateRecycleStrategy {
	
	/**
	 * 依赖的活动id
	 */
	private final int activityTypeId;
	
	public ActivityRecycleStrategy(int activityTypeId, int day, int hour) {
		super(day, hour);
		this.activityTypeId = activityTypeId;
	}
	
	public int getActivityTypeId() {
		return activityTypeId;
	}

	/**
	 * 通过活动id获取日期
	 */
	@Override
	public long calculateTimePoint(long time) {
		if (getDay() ==0 && getHour() == 0) {
			//没有配置时间, 则直接返回活动结束时间戳
			return getActivityCloseTime();
		}
		long timePoint = super.calculateTimePoint(time);
		long closeTime = getActivityCloseTime();
		if (timePoint > closeTime) {
			//有配置时间, 道具回收时间大于活动结束时间, 则返回活动结束时间
			return closeTime;
		}
		return timePoint;
	}
	
	/**
	 * 获取活动结束时间
	 * @return
	 */
	private long getActivityCloseTime() {
		IActivityService service = SpringContextHolder.getBean(IActivityService.class);
		IActivityType activityType = service.getActivityType(activityTypeId);
		if (activityType == null) {
			return 0L;
		}
		return activityType.getCloseTime();
	}

}
