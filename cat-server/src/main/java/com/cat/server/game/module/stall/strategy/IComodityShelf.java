package com.cat.server.game.module.stall.strategy;

import java.util.Collection;

import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;

/**
 * 货架接口类
 * @author Jeremy
 * @param <T>
 */
public interface IComodityShelf<T extends IResource> {
	
//	/**
//	 * 通过关键字查找
//	 * @param keyword
//	 * @return
//	 */
//	public Collection<T> search(String keyword);
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
	public void add(T t);
	
	/**
	 * 移除掉商品
	 * @param t
	 */
	public void remove(T t);
	
	/**
	 * 货架新增一批货物
	 * @param ts
	 */
	public void addAll(Collection<T> ts);
	
	/**
	 * 货架移除一批货物
	 * @param ts
	 */
	public void removeAll(Collection<T> ts);
	
}
