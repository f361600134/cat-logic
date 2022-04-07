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
	 *  从缓存通过id获取到域信息, 如果缓存无此玩家数据, 则返回null<br>
	 *  对于普通玩家, id即为playerId<br>
	 *  对于家族,id即为家族id<br>
	 *  对于排行榜,id即为排行榜类型id<br>
	 * @param id 唯一key
	 * @return
	 */
	public T getDomain(I id);
	
	/**
	 *  从缓存通过id获取到域信息, 如果缓存无此玩家数据, 则从数据库加载
	 *  对于普通玩家, id即为playerId<br>
	 *  对于家族,id即为家族id<br>
	 *  对于排行榜,id即为排行榜类型id<br>
	 * @param id 唯一key
	 * @return
	 */
	public T getOrLoadDomain(I id);
	
	
	/**
	 * 移除domain
	 * @param id
	 */
	public void remove(I id);

	/**
	 * 初始化
	 */
	default public void init(){}
	
	/**
	 * 销毁, 用于停服时的处理
	 */
	default public void destory(){}
	
	/**
	 * 从数据库获取数据
	 * @param id
	 * @return
	 */
	default public T getFromDb(I id) {
		return null;
	}
	
}
