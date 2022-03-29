package com.cat.server.game.module.functioncontrol;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigFunction;
import com.cat.server.game.helper.ModuleDefine;

/**
 * 用于处理功能重置
 * @auth Jeremy
 * @date 2022年3月28日下午9:38:24
 */
public interface IFunctionReset{
	
	/**
	 * 检查重置, 根据功能控制表内的时间DSL,检测是否重置, 如果重置则调用doReset方法
	 * @param playerId 玩家id
	 * @param now 当前时间
	 */
	default public void checkAndReset(long playerId, long now) {
		int functionId = this.getModuleId();
		ConfigFunction config = ConfigManager.getInstance().getConfig(ConfigFunction.class, functionId);
		long resetTime = config.getTime(now);
		if (this.getLastResetTime(playerId) != resetTime) {
			//判断最近一个重置时间的时间点,如果时间不一样, 则重置
			this.doReset(playerId, now);
			this.setLastResetTime(playerId, resetTime);
		}
	}
	
	public abstract void doReset(long playerId, long now);
	
	/**
	 * 获取功能id
	 * @param playerId
	 */
	public int getModuleId();
	
	/**
	 * 获取重置时间
	 * @return long  
	 * @date 2022年3月28日下午9:42:11
	 */
	public long getLastResetTime(long playerId);
	
	/**
	 * 设置重置时间
	 * @return long  
	 * @date 2022年3月28日下午9:42:11
	 */
	public void setLastResetTime(long playerId, long now);

}
