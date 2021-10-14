package com.cat.server.game.module.activity.time.point;

/**
 * 时间点
 * @author Jeremy
 * 
 */
public interface ITimePoint {
	
	 /**
     * 获取该时间戳之前的上一个时间点的时间戳<br>
     * 若该时间戳在时间点范围内 则返回当前时间戳
     * 
     * @param time
     * @return
     */
    long getLastTime(long time);

    /**
     * 获取该时间戳之后的下一个点的时间戳
     * 
     * @return
     */
    long getNextTime(long time);

    /**
     * 2个时间戳之间是否跨过了该时间点<br>
     * 若有时间戳在该时间点范围(time1,time2]内 也返回true
     * 
     * @param time1
     * @param time2
     * @return
     */
    boolean isAcross(long time1, long time2);
	
}
