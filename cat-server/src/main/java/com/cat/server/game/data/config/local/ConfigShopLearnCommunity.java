package com.cat.server.game.data.config.local;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigShopLearnCommunityBase;
import com.cat.server.game.data.config.local.interfaces.IConfigShop;


/**
 * sd.商店-研习社.xlsx<br>
 * shop_learn_community.json<br>
 * @author auto gen
 */
@ConfigPath("shop_learn_community.json")
public class ConfigShopLearnCommunity extends ConfigShopLearnCommunityBase implements IConfigShop {

	@Override
	public boolean isQuickBuy() {
		return this.getQuickBuy() == 1;
	}

}
