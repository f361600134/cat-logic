package com.cat.server.game.module.shop.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigShopControl;
import com.cat.server.game.data.config.local.ext.IConfigShop;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.cat.server.game.module.shop.domain.Shop;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.proto.PBShopInfoBuilder;
import com.cat.server.game.module.shop.strategy.IRefreshStrategy;
import com.cat.server.game.module.shop.strategy.RefreshStrategyEnum;

public abstract class AbstractShopType<T extends IConfigShop> implements IShopType{
	
	protected Class<T> shopConfigClazz;
	
	@Autowired protected IResourceGroupService resourceGroupService;
	@Autowired protected ConfigManager configManager;
	
	protected IRefreshStrategy refreshStrategy;

    @SuppressWarnings("unchecked")
    public AbstractShopType() {
    	Type superClass = getClass().getGenericSuperclass();
		this.shopConfigClazz = (Class<T>) (((ParameterizedType) superClass).getActualTypeArguments()[0]);
    }
	
	@Override
	public ErrorCode buy(ShopDomain domain, int configId, int number) {
		T config = this.getConfig(configId);
		if (config == null) {
			return ErrorCode.CONFIG_PARAM_ERROR;
		}
		//判断是否可以购买
		ErrorCode errorCode = this.checkCanBuy(domain, config, number);
		if (!errorCode.isSuccess()) {
			return errorCode;
		}
		//判断消耗
		if (!resourceGroupService.checkAndCost(domain.getId(), config.getPrice(), NatureEnum.ShopBuy)) {
			return ErrorCode.SHOP_COST_NOT_ENOUGH;
		}
		//增加到背包
		resourceGroupService.reward(domain.getId(), config.getItems(), NatureEnum.ShopBuy);
		//记录购买次数
		domain.addBuyCount(this.getShopType(), configId, number);
		//TODO log 看记录需要, 最好是分表记录, 不同商店购买日志记录在不同商店
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 取配置表内, 可以被一键购买的配置, 统一购买
	 * 一键购买规则有以下两种,当前选第一种
	 * 1. 可一键购买的所有商品,当钱足够时, 方可购买成功.
	 * 2.从第一件商品开始,钱够则卖,不够则中断.
	 */
	@Override
	public ErrorCode quickBuy(ShopDomain domain) {
		//筛选出可以被一键购买的商品列表
		Collection<T> configs = this.getConfigs()
				.values().stream()
				.filter((c)->c.isQuickBuy())
				.filter((c)-> this.checkCanBuy(domain, c, getRemainNumber(domain, c)).isSuccess())
				.collect(Collectors.toList());
		
		ResourceGroup costMap = new ResourceGroup();
		ResourceGroup itemMap = new ResourceGroup();
		for (T config : configs) {
			costMap.merge(config.getPrice());
			itemMap.merge(config.getItems());
		}
		//判断消耗
		if (!resourceGroupService.checkAndCost(domain.getId(), costMap, NatureEnum.ShopQuickBuy)) {
			return ErrorCode.SHOP_COST_NOT_ENOUGH;
		}
		//增加到背包
		resourceGroupService.reward(domain.getId(), itemMap, NatureEnum.ShopQuickBuy);
		//记录购买次数
		for (T config : configs) {
			domain.addBuyCount(this.getShopType(), config.getId(), this.getRemainNumber(domain, config));
		}
		//TODO 记录日志
		return ErrorCode.SUCCESS;
	}
	
	@Override
	public PBShopInfoBuilder toProto(ShopDomain domain) {
		PBShopInfoBuilder builder = PBShopInfoBuilder.newInstance();
		Shop shop = domain.getBean(this.getShopType());
		if (shop == null) {
			return builder;
		}
		builder.setShopId(this.getShopType());
		builder.setResRefreshNum(shop.getResRefreshNum());
		builder.setFreeRefreshNum(shop.getFreeRefreshNum());
		builder.setFreeRefreshTime(shop.getFreeRefreshTime());
		builder.addAllItemRecord(ResourceHelper.toPairProto(shop.getBuyedMap()));
		builder.addAllCommodities(this.refreshStrategy.getCurCommodities(domain));
		return builder;
	}
	
	/**
	 * 获取商品剩余数量
	 * @return int 剩余数量
	 * @date 2022年3月13日上午10:59:30
	 */
	public int getRemainNumber(ShopDomain domain, T config) {
		int limitNum = config.getLimit();
		int buyedCount = domain.getBuyCount(this.getShopType(), config.getId());
		int remain = limitNum - buyedCount;
		return remain;
	}
	
	/**
	 * 根据配置id获取配置
	 * @param configId 商品配置id
	 * @return T  商品配置
	 * @date 2022年3月13日上午10:01:13
	 */
	public T getConfig(int configId) {
		return ConfigManager.getInstance().getConfig(shopConfigClazz, configId);
	}
	
	/**
	 * 获取可购买的商品列表
	 * @param configId 商品配置id
	 * @return T  商品配置
	 * @date 2022年3月13日上午10:01:13
	 */
	public Map<Integer, T> getConfigs() {
		return ConfigManager.getInstance().getAllConfigs(shopConfigClazz);
	}
	
	/**
	 * 判断是否可以购买,等级/vip等级/已到达购买上下等限制.
	 * @param domain 玩家商店域
	 * @param config 当前购买配置
	 * @param number 当前可购买数量
	 * @return ErrorCode 错误码
	 */
	public ErrorCode checkCanBuy(ShopDomain domain, T config, int number) {
		if (number <= 0) {
			return ErrorCode.INVALID_PARAM;
		}
		//判断限购
		if (domain.getBuyCount(this.getShopType(), config.getId()) + number >= config.getLimit()) {
			return ErrorCode.SHOP_ITEM_LIMIT;
		}
		return ErrorCode.SUCCESS;
	}
	
	@Override
	public ErrorCode checkAndReset(ShopDomain domain, long now) {
		return this.refreshStrategy.checkAndReset(domain, now);
	}
	
	/**
	 * 获取刷新策略
	 * @return 刷新策略id
	 */
	public int getRefreshStrategy() {
		ConfigShopControl config = configManager.getConfig(ConfigShopControl.class, this.getShopType());
		return config == null ? 0 : config.getRefreshStrategy();
	}
	
	@Override
	public void start() throws Throwable {
		this.refreshStrategy = RefreshStrategyEnum.valueOf(getRefreshStrategy()).newStrategy(this);
	}
	
}
