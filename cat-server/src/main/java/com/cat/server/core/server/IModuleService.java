package com.cat.server.core.server;

/**
 * service层接口
 * @auth Jeremy
 * @date 2022年3月14日上午7:11:13
 */
public interface IModuleService {
	
	/**
	 * 检测功能是佛开启
	 * @return long 持有者id
	 */
	public abstract boolean checkModuleOpen(long id);

}
