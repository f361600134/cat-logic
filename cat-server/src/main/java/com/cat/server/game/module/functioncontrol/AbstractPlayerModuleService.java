package com.cat.server.game.module.functioncontrol;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.core.server.AbstractModuleService;

/**
 * 此类为AbstractService扩展
 * @auth Jeremy
 * @date 2022年3月14日上午7:36:16
 */
public abstract class AbstractPlayerModuleService extends AbstractModuleService {
	
	@Autowired private IFunctionControlService functionControlService;
	
	@Override
	public boolean checkModuleOpen(long id) {
		return functionControlService.checkOpen(id, this.getModuleId());
	}
	
//	/**
//	 * 获取玩家的重置时间
//	 * @param playerId
//	 * @return  
//	 * @return long  
//	 * @date 2022年3月14日上午7:43:23
//	 */
//	public abstract long getResetTime(long playerId);
//	
//	/**
//	 * 设置玩家的重置时间
//	 * @param playerId
//	 * @param now 
//	 */
//	public abstract void setResetTime(long playerId, long now);
	
	/**
	 * 设置玩家的重置时间
	 * @param playerId
	 * @param now 
	 */
	public abstract void checkAndReset(long playerId, long now);

}
