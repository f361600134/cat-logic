package com.cat.server.game.module.shop.strategy;

import java.util.Collection;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.shop.domain.ShopDomain;

/**
 * 刷新策略
 * @author Jeremy
 */
public interface IRefreshStrategy {
	
	/**
	 * 商店刷新
	 * @param domain
	 * @return
	 */
	ErrorCode refresh(ShopDomain domain, long now);
	
	/**
	 * 获取当前商店商品<br>
	 * 如果固定商店, 则返回所有商品
	 * 如果随机商店, 则返回缓存的商品列表
	 * @param domain
	 * @return
	 */
	Collection<Integer> getCurCommodities(ShopDomain domain);
	
	/**
	 * 商店重置
	 * @param domain
	 * @return
	 */
	ErrorCode checkAndReset(ShopDomain domain, long now);

}
