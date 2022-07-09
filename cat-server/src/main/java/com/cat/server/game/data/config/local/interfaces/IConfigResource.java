package com.cat.server.game.data.config.local.interfaces;

import com.cat.server.core.config.container.IGameConfig;

/**
 * 游戏内的商店抽象配置处理类
 * @author Jeremy
 */
public interface IConfigResource extends IGameConfig{
	
	/**
	 * 获取商品配置的名字
	 */
	public String getName();
	

}
