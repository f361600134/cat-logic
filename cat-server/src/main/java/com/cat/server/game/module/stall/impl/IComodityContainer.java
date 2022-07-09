package com.cat.server.game.module.stall.impl;

import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;

/**
 * 货架接口类
 * @author Jeremy
 * @param <T>
 */
public interface IComodityContainer {
	
	/**
	 * 通过关键字查找
	 * @param keyword 关键字查询, 如果为kong, 则查询全部
	 * @return
	 */
	public void search(String keyword, PBStallCommodityInfoBuilder builder);
	
	/**
	 * 货架新增一个摆摊商品
	 * @param t
	 */
	public void add(int configId, long uniqueId, int price);
	
	/**
	 * 移除掉商品
	 * @param t
	 */
	public void remove(int configId, long uniqueId);
	
	
}
