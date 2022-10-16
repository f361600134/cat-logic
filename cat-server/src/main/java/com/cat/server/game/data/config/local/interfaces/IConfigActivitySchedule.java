package com.cat.server.game.data.config.local.interfaces;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.game.module.activity.time.point.ITimePoint;

/**
 * 活动计划配置接口
 * @author Jeremy
 * @date 2022年10月15日下午11:07:19
 */
public interface IConfigActivitySchedule extends IGameConfig{
	
	/**
	 * 获取方案id
	 * @return  
	 * @return int  
	 * @date 2022年10月15日下午11:35:16
	 */
	public int getPlanId();
	
	  /** @return 准备时间*/
    public int getPrepareDuration();
    
    /**
     * @retur 获得开始持续时间
     */
    public int getBeginDuration();
    
    /** @return 结算持续时间*/
    public int getSettleDuration();
    
    /**
     * 获取活动激活的时间点, 进入准备阶段真正开始
     * @param now
     * @return  
     * @return long  
     * @date 2022年10月15日下午11:40:45
     */
    public ITimePoint getStartTimePoint();
	
	/**
	 * 活动开始,就进入准备阶段,所以准备阶段的时间,为配置的startTime时间点
	 * @param now
	 * @date 2022年10月15日下午7:34:34
	 */
	public long getPrepareTimestamp(long now);
	
	/**
	 * 获取活动正式开始的时间点,即为准备阶段结束时
	 * @param now
	 * @date 2022年10月15日下午7:34:34
	 */
	public long getBeginTimestamp(long now);
	
	/**
	 * 获取活动结算时间点,即为开始阶段结束时
	 * @param now
	 * @date 2022年10月15日下午7:34:34
	 */
	public long getSettleTimestamp(long now);
	
	/**
	 * 获取活动结束时间点,即为结算阶段结束时
	 * @param now
	 * @date 2022年10月15日下午7:34:34
	 */
	public long getCloseTimestamp(long now);

}
