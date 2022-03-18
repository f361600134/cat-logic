package com.cat.server.game.module.shop.strategy.impl;

import com.cat.server.game.data.config.local.ConfigShopControl;
import com.cat.server.game.data.config.local.ext.IConfigShop;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.strategy.AbstractRefreshStrategy;
import com.cat.server.game.module.shop.type.AbstractShopType;

/**
 * 资源刷新商品列表,不限制刷新次数
 * @author Jeremy
 */
public class ResRefreshNoLimitStrategy extends AbstractRefreshStrategy{
	
	public ResRefreshNoLimitStrategy(AbstractShopType<? extends IConfigShop> shopType) {
		super(shopType);
	}

	/**
	 * 仅判断资源数量是否足够, 不需要记录刷新次数
	 */
	@Override
	public ErrorCode checkRefresh(ConfigShopControl config, ShopDomain domain) {
		boolean bool = resourceGroupService.check(domain.getId(), config.getRefreshCost());
		if (!bool) {
			return ErrorCode.AMOUNT_NOT_ENOUGH;
		}
		return ErrorCode.SUCCESS;
	}

	@Override
	public void afterRefresh(ConfigShopControl config, ShopDomain domain) {
		resourceGroupService.cost(domain.getId(), config.getRefreshCost(), NatureEnum.ShopRefresh);
	}


}
