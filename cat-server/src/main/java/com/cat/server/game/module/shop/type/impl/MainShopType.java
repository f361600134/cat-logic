package com.cat.server.game.module.shop.type.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.cat.server.game.data.config.local.ConfigMainShop;
import com.cat.server.game.module.shop.assist.ShopTypeEnum;
import com.cat.server.game.module.shop.type.AbstractShopType;

/**
 * 主界面商店,通用商店
 * @auth Jeremy
 * @date 2022年3月13日下午12:19:56
 */
@Component
public class MainShopType extends AbstractShopType<ConfigMainShop>{

	@Override
	public int getShopType() {
		return ShopTypeEnum.MAIN_SHOP.getValue();
	}

	@Override
	public Map<Integer, Integer> getCost(ConfigMainShop config) {
		return config.getPrice().getDictionary();
	}

	@Override
	public Map<Integer, Integer> getItems(ConfigMainShop config) {
		return config.getItem().getDictionary();
	}

	@Override
	public int getLimitCount(ConfigMainShop config) {
		return config.getLimit();
	}

	@Override
	public boolean isQuickBuy(ConfigMainShop config) {
		return config.getQuickBuy() == 1;
	}

}
