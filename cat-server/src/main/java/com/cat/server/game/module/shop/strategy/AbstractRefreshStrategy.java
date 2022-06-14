package com.cat.server.game.module.shop.strategy;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.data.config.local.ConfigShopControl;
import com.cat.server.game.data.config.local.interfaces.IConfigShop;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.shop.domain.Shop;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.type.AbstractShopType;
import com.cat.server.utils.RandomUtil;
import com.cat.server.utils.TimeUtil;

/**
 * 抽象刷新策略
 * @author Jeremy
 */
public abstract class AbstractRefreshStrategy implements IRefreshStrategy {
	
	protected AbstractShopType<? extends IConfigShop> shopType;
	protected IResourceGroupService resourceGroupService;
	
	public AbstractRefreshStrategy(AbstractShopType<? extends IConfigShop> shopType) {
		this.shopType = shopType;
		this.resourceGroupService = SpringContextHolder.getBean(IResourceGroupService.class);
	}
	
	/**
	 * 默认随机指定数量的商品
	 */
	@Override
	public ErrorCode refresh(ShopDomain domain, long now) {
		ConfigShopControl config = ConfigManager.getInstance().getConfig(ConfigShopControl.class, shopType.getShopType());
		if (config == null) {
			return ErrorCode.CONFIG_NOT_EXISTS;
		}
		//检查刷新时间以及次数
		ErrorCode errorCode = this.checkRefresh(config, domain);
		if (!errorCode.isSuccess()) {
			return errorCode;
		}
		Collection<Integer> newCommodities = this.doRefresh(config, domain);
		domain.refresh(shopType.getShopType(), newCommodities);
		this.afterRefresh(config, domain);
		return ErrorCode.SUCCESS;
	}
	
	@Override
	public ErrorCode checkAndReset(ShopDomain domain, long now) {
		Shop shop = domain.getBean(shopType.getShopType());
		if (!TimeUtil.isSameDay(shop.getResetTime(), now)) {
			return ErrorCode.SHOP_REFRESH_NOT_IN_TIME;
		}
		ConfigShopControl config = ConfigManager.getInstance().getConfig(ConfigShopControl.class, shopType.getShopType());
		if (config == null) {
			return ErrorCode.CONFIG_NOT_EXISTS;
		}
		Collection<Integer> newCommodities = this.doRefresh(config, domain);
		domain.reset(shopType.getShopType(), config.getFreeRefreshNum(), config.getResRefreshNum(), newCommodities);
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 检查是否可以刷新
	 * @param config
	 * @param domain
	 * @return
	 */
	public abstract ErrorCode checkRefresh(ConfigShopControl config, ShopDomain domain);
	
	/**
	 * 处理随机刷新
	 * @param config
	 * @param domain
	 * @return
	 */
	public Collection<Integer> doRefresh(ConfigShopControl config, ShopDomain domain) {
		final int commoditiesNum = config.getCommoditiesNum();
		Collection<? extends IConfigShop> configs = shopType.getConfigs().values();
		//通过权重计算出随机商品列表
		List<Integer> result = RandomUtil.randomListByWeight(configs, commoditiesNum, false, IConfigShop::getWeight)
				.stream()
				.map(IConfigShop::getId)
				.collect(Collectors.toList());
		return result;
	}
	
	/**
	 * 刷新后的操作, 记录刷新时间刷新等
	 * @param config
	 * @param domain
	 * @return
	 */
	public abstract void afterRefresh(ConfigShopControl config, ShopDomain domain);

	/**
	 * 默认返回记录的商品列表
	 */
	@Override
	public Collection<Integer> getCurCommodities(ShopDomain domain) {
		return domain.getCommodities(shopType.getShopType());
	}
}
