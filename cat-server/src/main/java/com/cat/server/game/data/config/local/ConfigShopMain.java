package com.cat.server.game.data.config.local;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigShopMainBase;
import com.cat.server.game.data.config.local.ext.IConfigShop;


/**
 * sd.商店-主界面商店.xlsx<br>
 * shop_main.json<br>
 * @author auto gen
 */
@ConfigPath("shop_main.json")
public class ConfigShopMain extends ConfigShopMainBase implements IConfigShop {

	@Override
	public boolean isQuickBuy() {
		return this.getQuickBuy() == 1;
	}

}
