package com.cat.server.game.module.activity.define;

import java.util.concurrent.TimeUnit;

public class ActivityConstant {
	
	 /**
     * 活动数据,状态定时刷新检测时间间隔
     */
	public final static long TICK_INTERVAL = TimeUnit.MINUTES.toMillis(1);
	
	/**
     * 活动数据 定时自动保存间隔
     */
    public final static long AUTO_SAVE_INTERVAL = TimeUnit.MINUTES.toMillis(5);
    
    /**
     * 活动数据 定时自动保存所需的tick次数
     */
    public final static int AUTO_SAVE_TICK_COUNT = (int) (AUTO_SAVE_INTERVAL / TICK_INTERVAL);
	
    /**
     * 活动开始时长标记<br>
     * 2次活动时间(准备+开始+结算) 之间间隔1分钟
     */
    public final static int ACTIVITY_DURATION_INTERVAL_1_MINUTE = -1;

}
