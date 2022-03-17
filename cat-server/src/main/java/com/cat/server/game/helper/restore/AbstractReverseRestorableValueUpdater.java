package com.cat.server.game.helper.restore;

import com.cat.server.game.helper.log.NatureEnum;

/**
 * 抽象恢复数值处理器<br>
 * 用于数值反向恢复, 用于仅记录使用次数的情况<br>
 * 比如默认次数初始化为0, 使用次数后(大于0)开始恢复, x小时恢复1次进行扣除, 扣除到0次为止
 * @author Jeremy
 * @deprecated 貌似没有必要这样处理, 不适用于可以增加次数的情况
 */
public abstract class AbstractReverseRestorableValueUpdater<T extends RestorableValue> implements RestorableValueUpdater<T> {

    @Override
    public int getNum(long now) {
        checkAndRestore(now, true);
        return getHolder().getNum();
    }

    @Override
    public long getNextTime() {
        T holder = getHolder();
        long nextTime = holder.getLastTime();
        if (nextTime <= 0) {
            return RestorableValue.STOP_RESTORE_TIME;
        }
        return nextTime + getRefreshInterval();
    }

    @Override
    public void addNum(int addNum, boolean ignoreLimit, long now, boolean notify, NatureEnum nenum, Object... logParams) {
        if (addNum <= 0) {
            return;
        }
        checkAndRestore(now, notify);
        int maxLimit = getMaxLimit();
        T holder = getHolder();
        int oldNum = 0;
        int newNum = 0;
        synchronized (holder) {
            oldNum = holder.getNum();
            if (oldNum >= maxLimit && !ignoreLimit) {
                return;
            }
            newNum = oldNum + addNum;
            if (newNum >= maxLimit) {
                if (!ignoreLimit) {
                    newNum = maxLimit;
                }
                holder.setLastTime(RestorableValue.STOP_RESTORE_TIME);
            }
            holder.setNum(newNum);
        }
        afterChangeNum(oldNum, newNum, notify, nenum, logParams);
    }

    @Override
    public void reduceNum(int reduceNum, long now, boolean notify, NatureEnum nenum, Object... logParams) {
        if (reduceNum <= 0) {
            return;
        }
        checkAndRestore(now, notify);
        int maxLimit = getMaxLimit();
        T holder = getHolder();
        int oldNum = 0;
        int newNum = 0;
        synchronized (holder) {
            oldNum = holder.getNum();
            newNum = oldNum - reduceNum;
            if (newNum < 0) {
                newNum = 0;
            }
            if (newNum < maxLimit) {
                if (holder.getLastTime() <= 0) {
                    holder.setLastTime(now);
                }
            }
            holder.setNum(newNum);
        }
        afterChangeNum(oldNum, newNum, notify, nenum, logParams);
    }

    @Override
    public boolean checkAndRestore(long now, boolean notify) {
        int maxLimit = getMaxLimit();
        long refreshInterval = getRefreshInterval();
        T holder = getHolder();
        int oldNum = 0;
        int newNum = 0;
        synchronized (holder) {
            oldNum = holder.getNum();
            long lastTime = holder.getLastTime();
            if (oldNum >= maxLimit) {
                return false;
            }
            if (lastTime == RestorableValue.STOP_RESTORE_TIME) {
                // 之前停止恢复 从现在开始倒计时
                holder.setLastTime(now);
                return true;
            } else if (lastTime == 0 && oldNum == 0) {
                // 首次初始化
                holder.setNum(maxLimit);
                holder.setLastTime(RestorableValue.STOP_RESTORE_TIME);
                return true;
            }
            long nextTime = lastTime + refreshInterval;
            if (nextTime > now) {
                // 还没到时间恢复
                return false;
            }
            int restoreTimes = (int) ((now - lastTime) / refreshInterval);
            long newTime = lastTime + restoreTimes * refreshInterval;
            newNum = oldNum + restoreTimes;
            if (newNum >= maxLimit) {
                newNum = maxLimit;
                newTime = RestorableValue.STOP_RESTORE_TIME;
            }
            holder.setNum(newNum);
            holder.setLastTime(newTime);
        }
        afterChangeNum(oldNum, newNum, notify, getAutoRestoreLogCause());
        return true;
    }

    /**
     * 数值变化之后
     * 
     * @param oldNum
     * @param newNum
     * @param notify
     * @param logCause
     * @param logParams
     */
    protected abstract void afterChangeNum(int oldNum, int newNum, boolean notify, NatureEnum nenum, Object... logParams);

    /**
     * 自动恢复的日志原因类型
     * 
     * @return
     */
    protected NatureEnum getAutoRestoreLogCause() {
        return NatureEnum.ShopAutoRestore;
    }
}
