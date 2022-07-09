package com.cat.server.game.module.stall.type;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.stall.domain.Stall;
import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;

/**
 * 摆摊商品接口
 * @author Jeremy
 */
public interface IStallCommodity {
	
	/**
	 * 商品类型
	 * @return
	 */
	public int getType();
	
	/**
	 * 新增摆摊商品
	 * @param commodity
	 */
	public void add(Stall stall, long uniqueId);
	
	/**
	 * 移除摆摊商品
	 * @param commodity
	 */
	public void remove(Stall stall, long uniqueId);
	
	/**
	 * 检测摆摊商品是否可以上架, 不同装备检测方式不同
	 * @param commodity
	 */
	public ErrorCode check(long playerId, long uniqueId);
	
	/**
	 * 商品列表转协议对象
	 * @return
	 */
	public void toProto(Stall stall, PBStallCommodityInfoBuilder builder);
	
}
