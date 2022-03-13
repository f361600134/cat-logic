package com.cat.server.game.module.shop.type.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.cat.server.game.data.config.local.ConfigLearnCommunityShop;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.cat.server.game.module.activity.type.impl.LearnCommunityActivityType;
import com.cat.server.game.module.shop.assist.ShopTypeEnum;
import com.cat.server.game.module.shop.type.AbstractActivityShopType;

/**
 *  研习社商店
 * @auth Jeremy
 * @date 2022年3月13日下午12:18:39
 */
@Component
public class LearnCommunityShopType extends AbstractActivityShopType<ConfigLearnCommunityShop, LearnCommunityActivityType>{

	@Override
	public int getActivityTypeId() {
		return ActivityTypeEnum.LEARN_COMMUNITY.getValue();
	}

	@Override
	public int getConfigActivityId(ConfigLearnCommunityShop config) {
		return config.getActivityId();
	}

	@Override
	public int getShopType() {
		return ShopTypeEnum.LEARNCOMMUNUTY_SHOP.getValue();
	}

	@Override
	public Map<Integer, Integer> getCost(ConfigLearnCommunityShop config) {
		return config.getPrice().getDictionary();
	}

	@Override
	public Map<Integer, Integer> getItems(ConfigLearnCommunityShop config) {
		return config.getItem().getDictionary();
	}

	@Override
	public int getLimitCount(ConfigLearnCommunityShop config) {
		return config.getLimit();
	}

	@Override
	public boolean isQuickBuy(ConfigLearnCommunityShop config) {
		return config.getQuickBuy() == 1;
	}

}
