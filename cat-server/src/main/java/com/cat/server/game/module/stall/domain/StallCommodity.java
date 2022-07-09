package com.cat.server.game.module.stall.domain;

/**
 * 货架商品, 没有用到
 * @author Jeremy
 */
public class StallCommodity {

	/**
	 * 商品id
	 */
	private final long uniqueId;
	
	/**
	 * 商品类型
	 */
	private final int configId;
	
	/**
	 * 消耗资源数量
	 */
	private final int cost;

	public long getUniqueId() {
		return uniqueId;
	}

	public int getConfigId() {
		return configId;
	}

	public int getCost() {
		return cost;
	}

	public StallCommodity(long uniqueId, int configId, int cost) {
		super();
		this.uniqueId = uniqueId;
		this.configId = configId;
		this.cost = cost;
	}
	
	public static StallCommodity create(long uniqueId, int configId, int cost) {
		return new StallCommodity(uniqueId, configId, cost);
	}
}
