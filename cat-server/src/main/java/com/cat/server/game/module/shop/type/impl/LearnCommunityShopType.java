package com.cat.server.game.module.shop.type.impl;

import org.springframework.stereotype.Component;

import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.data.config.local.ConfigShopLearnCommunity;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.cat.server.game.module.activityoperation.learncommunity.LearnCommunityActivityType;
import com.cat.server.game.module.shop.assist.ShopTypeEnum;
import com.cat.server.game.module.shop.type.AbstractActivityShopType;

/**
 *  研习社商店
 * @auth Jeremy
 * @date 2022年3月13日下午12:18:39
 */
@Component
public class LearnCommunityShopType extends AbstractActivityShopType<ConfigShopLearnCommunity, LearnCommunityActivityType> {

	@Override
	public int getShopType() {
		return ShopTypeEnum.LEARNCOMMUNUTY_SHOP.getValue();
	}
	
	@Override
	public int getActivityTypeId() {
		return ActivityTypeEnum.LEARN_COMMUNITY.getValue();
	}

	@Override
	public int getConfigActivityId(ConfigShopLearnCommunity config) {
		return config.getActivityId();
	}
	
	@Override
	public int priority() {
		return Priority.LOWEST.getPriority();
	}

}
