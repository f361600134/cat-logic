package com.cat.server.game.module.activity.time.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;

/**
 * 多时间点的抽象类, 一个表达式可以涵盖多个时间点, 但是一段时间仅只有一个时间点.<br>
 * 例如, 表达式 1o_16c_2ct,表示开服第一天活动开始, 16天后开启循环, 共循环2次.<br>
 * 那么这个时间段内, 活动开始时间会有两个时间点, 第一个时间点为开服时间, 第二个时间点为16天后的0点<br>
 * 
 * @author Jeremy
 */
public abstract class AbstractMultiTimePoint implements ITimePoint {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected long lastTime;

	protected long nextTime;

	public long getLastTime(long time) {
		checkAndRefresh(time);
		return lastTime;
	}

	public long getNextTime(long time) {
		checkAndRefresh(time);
		return nextTime;
	}
	
	@Override
	public long getUniqueTime() {
		return getLastTime(TimeUtil.now());
	}

	/**
	 * 检查并尝试刷新时间
	 * 
	 * @param time
	 * @param stamp 当前读锁标记
	 * @return stamp 当前使用的锁标记(若升级为写锁 则为写锁标记)
	 */
	protected void checkAndRefresh(long time) {
		if (!needCalculateTime(time)) {
			return;
		}
		try {
			Pair<Long, Long> timePair = calculateTime(time);
			lastTime = timePair.getLeft();
			nextTime = timePair.getRight();
		} catch (Exception e) {
			logger.error("calculateTime error.", e);
		}
	}

	/**
	 * 计算时间
	 * 
	 * @param time
	 * @return
	 */
	protected abstract Pair<Long, Long> calculateTime(long time);

	/**
	 * 判断是否可以计算时间
	 * 
	 * @param time
	 * @return
	 */
	protected abstract boolean needCalculateTime(long time);

}
