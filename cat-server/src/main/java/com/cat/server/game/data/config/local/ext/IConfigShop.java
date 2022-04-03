package com.cat.server.game.data.config.local.ext;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.game.module.resource.domain.ResourceGroup;

/**
 * 游戏内的商店抽象配置处理类
 * @author Jeremy
 */
public interface IConfigShop extends IGameConfig{
	
	/**
	 * 获取价格
	 * @return Map<Integer,Integer>  价格map
	 * @date 2022年3月12日下午3:14:48
	 */
	public ResourceGroup getPrice();
	
	/**
	 * 获取购买所得
	 * @return  
	 * @return Map<Integer,Integer>  购买所得
	 * @date 2022年3月12日下午3:14:48
	 */
	public ResourceGroup getItems();
	
	/**
	 * 判断限购次数,有些商店的商品如果是vip可以多购买一次
	 * @return  
	 * @return Map<Integer,Integer>  购买所得
	 * @date 2022年3月12日下午3:14:48
	 */
	public int getLimit();
	
	/**
	 * 获取权重
	 * @return 权重值
	 */
	public int getWeight();
	
	/**
	 * 是否可以被一键购买
	 */
	public boolean isQuickBuy();
	

}
