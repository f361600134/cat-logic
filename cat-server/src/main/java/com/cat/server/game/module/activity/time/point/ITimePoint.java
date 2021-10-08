package com.cat.server.game.module.activity.time.point;

/**
 * 时间点
 * @author Jeremy
 * 
 */
public interface ITimePoint {
	
	/***
	 * 时间点唯一的时间戳
	 * @return
	 */
	long getUniqueTime();
	
	 /**
     * 2个时间戳之间是否跨过了该时间点<br>
     * 若有时间戳在该时间点范围(time1,time2]内 也返回true
     * 
     * @param time1
     * @param time2
     * @return
     */
    default boolean isAcross(long time1, long time2) {
    	 long unique = getUniqueTime();
         if (unique > time1 && unique <= time2) {
             return true;
         }
         return false;
    }

}
