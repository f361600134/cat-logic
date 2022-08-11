package com.cat.server.game.module.function;

import com.cat.server.core.lifecycle.ILifecycle;

/**
 * 功能的开启关闭, 由策划配置以及后台控制
 * 策划配置用于屏蔽, 开启相关功能
 * 后台配置用于, 当模块功能出现了问题,通过后台停掉该功能
 * @auth Jeremy
 * @date 2022年3月13日下午9:37:45
 */
public interface IFunctionService extends IPlayerModuleService, ILifecycle{
	
	/**
	 * 检查功能是否开启<br>
	 * 1. 校验配置是否屏蔽了功能<br>
	 * 2. 校验后台是否强制关闭了功能<br>
	 * 3. 校验玩家是否开启了此功能<br>
	 * 4. 如存在其他校验,后续补充
	 * @param playerId 玩家id
	 * @param functionId 功能id
	 * @return boolean true:开启, false:关闭
	 */
	boolean checkOpen(long playerId, int functionId);
	
	/**
	 * 获取功能的最后重置时间
	 * @param playerId
	 * @param functionId
	 * @return long  最后重置时间
	 * @date 2022年3月28日下午10:18:34
	 */
	long getLastResetTime(long playerId, int functionId);
	
	/**
	 * 设置功能的最后重置时间
	 * @param playerId
	 * @param functionId
	 * @return long  最后重置时间
	 * @date 2022年3月28日下午10:18:34
	 */
	void setLastResetTime(long playerId, int functionId, long now);

}