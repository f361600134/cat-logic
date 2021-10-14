package com.cat.server.game.module.activity.time.point.impl;

import java.util.concurrent.TimeUnit;

import com.cat.server.game.module.activity.time.point.AbstractMultiTimePoint;
import com.cat.server.game.module.activity.time.point.ITimePoint;
import com.cat.server.utils.Pair;

/**
 * 循环的时间点
 * 
 * @author Jeremy
 */
public class CycleTimePoint extends AbstractMultiTimePoint {

	/**
	 * 首个时间点<br>
	 * 不管是通过开服时间计算时间点, 还是指定某个时间计算时间点, 都需要一个最初的时间点<br>
	 * 
	 */
	private ITimePoint firstTimePoint;
	/**
	 * 循环<br>
	 * 天<br>
	 * 1为从首个时间点开始后 每天
	 */
	private final int cycle;
	/**
	 * 循环次数
	 */
	private final int cycleTimes;

	/**
	 * 上次计算时 使用的首个时间点
	 */
	private long oldFirstTime;
	/**
	 * 停止循环时间<br>
	 * 如果大于0, 则表示指定了循环次数<br>
	 * 否则表示无限循环
	 */
	private long stopCycleTime;

	public ITimePoint getFirstTimePoint() {
		return firstTimePoint;
	}

	public int getCycle() {
		return cycle;
	}

	public int getCycleTimes() {
		return cycleTimes;
	}

	public long getOldFirstTime() {
		return oldFirstTime;
	}

	public long getStopCycleTime() {
		return stopCycleTime;
	}

	public CycleTimePoint(ITimePoint firstTimePoint, int cycle, int cycleTimes) {
		if (firstTimePoint == null) {
			throw new NullPointerException("firstPoint is null.");
		}
		if (cycle <= 0) {
			throw new IllegalArgumentException("cycle must be greater than zero.");
		}
		this.firstTimePoint = firstTimePoint;
		this.cycle = cycle;
		this.cycleTimes = cycleTimes;
		if (cycleTimes > 0) {
			long cycleTime = TimeUnit.DAYS.toMillis(cycle);
			this.stopCycleTime = this.oldFirstTime + cycleTime * (cycleTimes - 1);
		} else {
			this.stopCycleTime = Long.MAX_VALUE;
		}
	}

	@Override
	protected Pair<Long, Long> calculateTime(long time) {
		long firstTime = firstTimePoint.getLastTime(time);
		if (firstTime != oldFirstTime && stopCycleTime != Long.MAX_VALUE) {// 重新计算不再循环时间
			long cycleTime = TimeUnit.DAYS.toMillis(cycle);
			this.stopCycleTime = this.oldFirstTime + cycleTime * (cycleTimes - 1);
		}
		if (time >= firstTime && time < stopCycleTime) {
			// 已过了首次的时间点
			long cycleTime = TimeUnit.DAYS.toMillis(cycle);
			long throughTime = time - firstTime;
			long count = throughTime / cycleTime;
			lastTime = firstTime + cycleTime * count;
			nextTime = lastTime + cycleTime;
		} else if (time >= stopCycleTime) {
			lastTime = stopCycleTime;
			nextTime = stopCycleTime;
		}
		oldFirstTime = firstTime;
		return Pair.of(lastTime, nextTime);
	}

	@Override
	protected boolean needCalculateTime(long time) {
		if (lastTime == 0 && nextTime == 0) {
			// 未计算过
			return true;
		}
		long firstTime = firstTimePoint.getLastTime(time);
        if (firstTime != oldFirstTime) {
            // 开始时间点发生了变化
            return true;
        }
        if (time < oldFirstTime) {
            // 比开始时间点早
            return false;
        }
        //过了不再循环时间
        if (lastTime == stopCycleTime) {
            return false;
        }
        // 比开始时间点晚
        if (lastTime > time || nextTime <= time) {
            return true;
        }
		return false;
	}

}
