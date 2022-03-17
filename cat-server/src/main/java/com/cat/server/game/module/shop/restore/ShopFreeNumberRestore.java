package com.cat.server.game.module.shop.restore;

import java.util.concurrent.TimeUnit;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigShopControl;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.restore.AbstractRestorableValueUpdater;
import com.cat.server.game.module.shop.domain.Shop;

/**
 * 商店免费次数恢复器
 * @author Jeremy
 */
public class ShopFreeNumberRestore extends AbstractRestorableValueUpdater<Shop>{
	
	private Shop shop;
	
	public ShopFreeNumberRestore(Shop shop) {
		this.shop = shop;
	}

	@Override
	public Shop getHolder() {
		return shop;
	}

	@Override
	public int getMaxLimit() {
		ConfigShopControl config = ConfigManager.getInstance().getConfig(ConfigShopControl.class, shop.getShopId());
		return config == null ? 0 : config.getFreeRefreshNum();
	}

	@Override
	public long getRefreshInterval() {
		ConfigShopControl config = ConfigManager.getInstance().getConfig(ConfigShopControl.class, shop.getShopId());
		return config == null ? 0L : TimeUnit.HOURS.toMillis(config.getFreeRefreshRecover());
	}

	@Override
	protected void afterChangeNum(int oldNum, int newNum, boolean notify, NatureEnum nenum, Object... logParams) {
		// TODO something
		// 1. 持久化到数据库
		this.shop.update();
		// 2. 记录日志
	}

}
