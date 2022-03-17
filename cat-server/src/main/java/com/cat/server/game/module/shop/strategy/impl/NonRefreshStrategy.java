package com.cat.server.game.module.shop.strategy.impl;

import java.util.Collection;

import com.cat.server.game.data.config.local.ConfigShopControl;
import com.cat.server.game.data.config.local.ext.IConfigShop;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.strategy.AbstractRefreshStrategy;
import com.cat.server.game.module.shop.type.AbstractShopType;

/**
 * 无刷新策略
 * @author Jeremy
 */
public class NonRefreshStrategy extends AbstractRefreshStrategy{
	
	public NonRefreshStrategy(AbstractShopType<? extends IConfigShop> shopType) {
		super(shopType);
	}

	@Override
	public ErrorCode refresh(ShopDomain domain, long now) {
		return ErrorCode.SHOP_REFRESH_CANNOT;
	}

	@Override
	public Collection<Integer> getCurCommodities(ShopDomain domain) {
		return shopType.getConfigs().keySet();
	}

	@Override
	public ErrorCode checkRefresh(ConfigShopControl config, ShopDomain domain) {
		return ErrorCode.SHOP_REFRESH_CANNOT;
	}

	@Override
	public void afterRefresh(ConfigShopControl config, ShopDomain domain) {
		// TO DO nothing.
	}

}
