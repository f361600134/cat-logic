package com.cat.server.game.module.shop.type;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.shop.domain.ShopDomain;

public interface IShopType {
	
	/**
	 * 获取商店类型
	 * @return  
	 * @return int  
	 * @date 2022年3月13日上午10:55:20
	 */
	public int getShopType();
	
	/**
	 * 购买商品
	 * @param ShopDomain 玩家商店域
	 * @param configid 商品id
	 * @param number 数量
	 * @date 2022年3月12日下午3:01:34
	 */
	ErrorCode buy(ShopDomain domain, int configId, int number);
	
	/**
	 * 一键购买
	 * @param ShopDomain 玩家商店域
	 * @date 2022年3月12日下午3:01:34
	 */
	ErrorCode quickBuy(ShopDomain domain);
	
	/**
	 * 商店刷新
	 * @param domain
	 * @return
	 */
	ErrorCode refresh(ShopDomain domain);
	
}
