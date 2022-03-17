package com.cat.server.game.module.shop.type.impl;

import org.springframework.stereotype.Component;

import com.cat.server.game.data.config.local.ConfigShopMain;
import com.cat.server.game.module.shop.assist.ShopTypeEnum;
import com.cat.server.game.module.shop.type.AbstractShopType;

/**
 * 主界面商店,通用商店
 * @auth Jeremy
 * @date 2022年3月13日下午12:19:56
 */
@Component
public class MainShopType extends AbstractShopType<ConfigShopMain>{

	@Override
	public int getShopType() {
		return ShopTypeEnum.MAIN_SHOP.getValue();
	}

}
