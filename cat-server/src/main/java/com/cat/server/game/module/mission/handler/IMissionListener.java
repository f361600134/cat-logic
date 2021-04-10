package com.cat.server.game.module.mission.handler;

import com.cat.server.game.module.mission.type.IMission;

/**
 * 任务处理监听器, 用于处理任务后的操作
 * @auth Jeremy
 * @date 2021年2月28日下午9:45:37
 */
@FunctionalInterface
public interface IMissionListener<V> {
	
	/**
	 * 任务回调
	 * @param v
	 * @return  
	 * @return R  
	 * @date 2021年2月28日下午9:50:14
	 */
	public void call(IMission mission, V v);

}
