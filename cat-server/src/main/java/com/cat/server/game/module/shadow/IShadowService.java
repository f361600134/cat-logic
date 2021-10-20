package com.cat.server.game.module.shadow;

import com.cat.server.game.module.shadow.domain.Shadow;

/**
 * Shadow接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IShadowService {
	
	/**
	 * 根据玩家id获取影子数据<br>
	 * 先从缓存获取, 获取不到从数据库获取, 数据库获取不到返回null
	 * @return null or 影子数据
	 */
	public Shadow get(long playerId);
	
	/**
	 * 根据玩家id获取影子数据<br>
	 * 先从缓存获取, 获取不到从数据库获取, 数据库获取不到创建一个影子对象
	 * @return null or 影子数据
	 */
	public Shadow getOrCreate(long playerId);

}