package com.cat.server.game.helper.restore;

import com.cat.server.game.helper.log.NatureEnum;

/**
 * 处理 {@link RestorableValue} 的数值更新 处理可恢复数值的更新<br>
 * @author redback
 */
public interface RestorableValueUpdater<T extends RestorableValue> {

    /**
     * 恢复对象持有者
     * @return
     */
    T getHolder();

    /**
     * 获取自动恢复的数量上限值
     * 
     * @return
     */
    int getMaxLimit();

    /**
     * 获取刷新间隔<br>
     * 毫秒
     * 
     * @return
     */
    long getRefreshInterval();

    /**
     * 获取该体力类数值的值
     * 
     * @param now
     * @return
     */
    int getNum(long now);

    /**
     * 获取下次恢复时间<br>
     * 若不会恢复 则返回-1<br>
     * 不会触发检查<br>
     * 
     * @return
     */
    long getNextTime();

    /**
     * 增加数值
     * 
     * @param addNum
     * @param ignoreLimit
     * @param now
     * @param notify
     * @param logCause
     * @param logParams
     */
    void addNum(int addNum, boolean ignoreLimit, long now, boolean notify, NatureEnum nenum, Object... logParams);

    /**
     * 扣除数值
     * 
     * @param reduceNum
     * @param now
     * @param notify
     * @param logCause
     * @param logParams
     */
    void reduceNum(int reduceNum, long now, boolean notify, NatureEnum nenum, Object... logParams);

    /**
     * 检测并恢复数值
     * 
     * @param now    当前时间
     * @param notify
     * @return value是否发生了变化
     */
    boolean checkAndRestore(long now, boolean notify);

}
