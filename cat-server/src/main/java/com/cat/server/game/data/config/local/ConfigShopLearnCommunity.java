package com.cat.server.game.data.config.local;

import java.util.Map;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigShopLearnCommunityBase;
import com.cat.server.game.data.config.local.ext.IConfigShop;


/**
 * sd.商店-研习社(活动).xlsx<br>
 * shop_learn_community.json<br>
 * @author auto gen
 */
@ConfigPath("shop_learn_community.json")
public class ConfigShopLearnCommunity extends ConfigShopLearnCommunityBase implements IConfigShop {

	@Override
	public Map<Integer, Integer> getCost() {
		return this.getPrice().getDictionary();
	}

	@Override
	public Map<Integer, Integer> getItems() {
		return this.getItem().getDictionary();
	}

	@Override
	public int getLimitCount() {
		return this.getLimit();
	}

	@Override
	public boolean isQuickBuy() {
		return this.getQuickBuy() == 1;
	}
	
}
