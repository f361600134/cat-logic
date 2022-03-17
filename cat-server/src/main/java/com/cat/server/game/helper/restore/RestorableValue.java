package com.cat.server.game.helper.restore;

/**
 * 可自动回复的值<br>
 * 可将该接口实现类作为体力类数值的参数类型<br>
 * @author hdh
 */
public interface RestorableValue {
    /**
     * 停止自动恢复时间
     */
    long STOP_RESTORE_TIME = -1;
    /**
     * 获取当前数量
     * @return
     */
    int getNum();
    /**
     * 设置当前数量
     * @param num
     */
    void setNum(int num);
    /**
     * 获取上次操作/回复时间
     * @return
     */
    long getLastTime();
	/**
	 * 设置最后恢复的时间
	 * @param lastTime
	 */
    void setLastTime(long lastTime);

}
