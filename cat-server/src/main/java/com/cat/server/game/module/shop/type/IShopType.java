package com.cat.server.game.module.shop.type;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.proto.PBShopInfoBuilder;

/**
 * 商店类型处理
 * @author Jeremy
 */
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
	 * 获取当前商店商品<br>
	 * 如果固定商店, 则返回所有商品
	 * 如果随机商店, 则返回缓存的商品列表
	 * @param domain
	 * @return
	 */
	PBShopInfoBuilder toProto(ShopDomain domain);
	
	/**
	 * 用于重置
	 * @param ShopDomain 玩家商店域
	 * @date 2022年3月12日下午3:01:34
	 */
	ErrorCode checkAndReset(ShopDomain domain, long now);
	
	
}
