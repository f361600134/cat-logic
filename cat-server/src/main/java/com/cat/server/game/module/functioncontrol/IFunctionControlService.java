package com.cat.server.game.module.functioncontrol;

/**
 * 功能的开启关闭, 由策划配置以及后台控制
 * 策划配置用于屏蔽, 开启相关功能
 * 后台配置用于, 当模块功能出现了问题,通过后台停掉该功能
 * @auth Jeremy
 * @date 2022年3月13日下午9:37:45
 */
public interface IFunctionControlService {
	
	/**
	 * 检查功能是否开启
	 * @param moduleId
	 * @return boolean  
	 */
	boolean checkOpen(long playerId, int moduleId);
	
}
