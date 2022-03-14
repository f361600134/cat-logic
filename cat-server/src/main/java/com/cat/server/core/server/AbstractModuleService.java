package com.cat.server.core.server;

/**
 * service层抽象
 * @auth Jeremy
 * @date 2022年3月14日上午7:11:42
 */
public abstract class AbstractModuleService implements IModuleService {
	
	/**
	 * 获取功能id
	 * @return int  
	 */
	public abstract int getModuleId();
	
}
