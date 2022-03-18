package com.cat.server.game.module.shop.strategy.impl;

import com.cat.server.game.data.config.local.ConfigShopControl;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.shop.domain.Shop;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.strategy.AbstractRefreshStrategy;
import com.cat.server.game.module.shop.type.AbstractShopType;

/**
 * 限制资源刷新次数(3次),免费刷新次数(3次),免费次数不足时开始倒计时恢复
 * @author Jeremy
 */
public class ResRefreshAndFreeRefreshRecoverStrategy extends AbstractRefreshStrategy{
	
	public ResRefreshAndFreeRefreshRecoverStrategy(AbstractShopType<?> shopType) {
		super(shopType);
	}

	/**
	 * 1. 检测免费次数
	 * 2. 检测付费次数
	 */
	@Override
	public ErrorCode checkRefresh(ConfigShopControl config, ShopDomain domain) {
		Shop shop = domain.getBean(shopType.getShopType());
		if (shop.getFreeRefreshNum() + 1 < config.getFreeRefreshNum()) {
			return ErrorCode.SUCCESS;
		}else if (shop.getResRefreshNum() + 1 < config.getResRefreshNum()) {
			if (!resourceGroupService.check(domain.getId(), config.getRefreshCost())) {
				//次数足够, 但是消耗不足
				return ErrorCode.SHOP_COST_NO_ENOUGH;
			}
			//付费次数足够, 并且消耗资源足够, 返回检测成功
			return ErrorCode.SUCCESS;
		}
		return ErrorCode.SHOP_NO_REFRESH;
	}

	@Override
	public void afterRefresh(ConfigShopControl config, ShopDomain domain) {
		Shop shop = domain.getBean(shopType.getShopType());
		if (shop.getFreeRefreshNum() + 1 < config.getFreeRefreshNum()) {
			domain.addFreeRefreshNum(shopType.getShopType());
		}else if (shop.getResRefreshNum() + 1 < config.getResRefreshNum()) {
			domain.addResRefreshNum(shopType.getShopType());
			resourceGroupService.cost(domain.getId(), config.getRefreshCost(), NatureEnum.ShopRefresh);
		}
	}

}
