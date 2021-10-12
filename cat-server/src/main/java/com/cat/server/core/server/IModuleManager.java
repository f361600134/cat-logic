package com.cat.server.core.server;

import java.util.Collection;

/**
 * 	模块Manager接口
 * @author Jeremy
 * @param <T>
 */
public interface IModuleManager<I, T> {
	
	/**
	 * 获取所有domain
	 * @return
	 */
	public Collection<T> getAllDomain();
	
	/**
	 *  通过id获取到域信息
	 *  对于普通玩家, id即为playerId
	 *  对于家族,id即为家族id
	 *  对于帮派,id即为排行榜类型id
	 * @param id
	 * @return
	 */
	public T getDomain(I id);
	
	/**
	 * 
	 * @param id
	 */
	public void remove(I id);

	/**
	 * 初始化
	 */
	default public void init(){}

}
