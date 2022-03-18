package com.cat.server.game.module.shop.strategy.impl;

import com.cat.server.game.data.config.local.ConfigShopControl;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.shop.domain.Shop;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.strategy.AbstractRefreshStrategy;
import com.cat.server.game.module.shop.type.AbstractShopType;

/**
 * 可以资源刷新,限制资源刷新次数
 * @author Jeremy
 */
public class ResRefreshLimitStrategy extends AbstractRefreshStrategy{
	
	public ResRefreshLimitStrategy(AbstractShopType<?> shopType) {
		super(shopType);
	}

	/**
	 * 限制资源刷新次数, 先检测次数, 再扣资源
	 */
	@Override
	public ErrorCode checkRefresh(ConfigShopControl config, ShopDomain domain) {
		Shop shop = domain.getBean(shopType.getShopType());
		final int count = config.getResRefreshNum();
		if (shop.getResRefreshNum() + 1 > count) {
			return ErrorCode.SHOP_NO_REFRESH;
		}
		boolean bool = resourceGroupService.check(domain.getId(), config.getRefreshCost());
		if (!bool) {
			return ErrorCode.AMOUNT_NOT_ENOUGH;
		}
		return ErrorCode.SUCCESS;
	}

	@Override
	public void afterRefresh(ConfigShopControl config, ShopDomain domain) {
		domain.addResRefreshNum(shopType.getShopType());
		resourceGroupService.cost(domain.getId(), config.getRefreshCost(), NatureEnum.ShopRefresh);
	}

}
