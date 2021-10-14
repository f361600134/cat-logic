package com.cat.server.game.module.activity.time.point;

public interface IUniqueTimePoint extends ITimePoint {
	
	/**
     * 该时间点对应的唯一的时间戳
     * 
     * @return
     */
    long getUniqueTime();

    @Override
    default long getLastTime(long time) {
        return getUniqueTime();
    }

    @Override
    default long getNextTime(long time) {
        return getUniqueTime();
    }

    @Override
    default boolean isAcross(long time1, long time2) {
        long unique = getUniqueTime();
        if (unique > time1 && unique <= time2) {
            return true;
        }
        return false;
    }

}
