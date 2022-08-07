package com.cat.server.core.server;

/**
 * service层接口
 * @auth Jeremy
 * @date 2022年3月14日上午7:11:13
 */
public interface IModuleService {
	
	/**
	 * 检测功能是否开启,协议层阻断消息
	 * @return long 持有者id
	 */
	boolean checkModuleOpen(long playerId);
	
	/**
	 * 获取功能id
	 * @return int  
	 */
	public int getModuleId();
	
}
