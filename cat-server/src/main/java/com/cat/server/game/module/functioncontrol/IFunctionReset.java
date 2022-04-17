package com.cat.server.game.module.functioncontrol;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.data.config.local.ConfigFunction;
import com.cat.server.game.module.functioncontrol.time.point.IResetTimePoint;

/**
 * 用于处理功能重置
 * @auth Jeremy
 * @date 2022年3月28日下午9:38:24
 */
public interface IFunctionReset{
	
	/**
	 * 获取功能id
	 * @param playerId
	 */
	public int getModuleId();
	
	/**
	 * 检查重置, 根据功能控制表内的时间DSL,检测是否重置, 如果重置则调用doReset方法
	 * @param playerId 玩家id
	 * @param now 当前时间
	 */
	default public boolean checkAndReset(long playerId, long now) {
		int functionId = this.getModuleId();
		ConfigFunction config = ConfigManager.getInstance().getConfig(ConfigFunction.class, functionId);
		if (config == null) {
			return true;
		}
		long resetTime = config.getResetTimePoint().getResetTimePoint(now);
		if (this.getLastResetTime(playerId) != resetTime && resetTime!= IResetTimePoint.RESET_FREE) {
			//判断最近一个重置时间的时间点,如果时间不一样, 则重置
			this.doReset(playerId, now);
			this.setLastResetTime(playerId, resetTime);
			return true;
		}
		return false;
	}
	
	/**
	 * 获取重置时间
	 * @return long  
	 * @date 2022年3月28日下午9:42:11
	 */
	default public long getLastResetTime(long playerId) {
		IFunctionControlService service = SpringContextHolder.getBean(IFunctionControlService.class);
		if (service == null) {
			return 0L;
		}
		return service.getLastResetTime(playerId, getModuleId());
	}
	
	/**
	 * 设置重置时间
	 * @return long  
	 * @date 2022年3月28日下午9:42:11
	 */
	default void setLastResetTime(long playerId, long now) {
		IFunctionControlService service = SpringContextHolder.getBean(IFunctionControlService.class);
		if (service == null) {
			return;
		}
		service.setLastResetTime(playerId, getModuleId(), now);
	}
	
	/**
	 * 用于处理重置细节操作,每个模块重置业务不一样
	 * @param playerId 玩家id
	 * @param now 时间戳
	 */
	public abstract void doReset(long playerId, long now);
	
}
