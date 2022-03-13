package com.cat.server.game.module.shop.domain;

import java.util.HashMap;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.cat.server.game.module.shop.proto.PBShopInfoBuilder; 

/**
* @author Jeremy
*/
@PO(name = "shop")
public class Shop extends ShopPo implements IPersistence{
	
	/**
	 * 已购买列表
	 */
	@Column(value = PROP_BUYEDSTR, clazzType = HashMap.class)
	private Map<Integer, Integer> buyedMap;

	public Shop() {

	}
	
	public Shop(long playerId) {
		this.playerId = playerId;
	}

	Map<Integer, Integer> getBuyedMap() {
		return buyedMap;
	}
	
	/**
	 * 转协议对象
	 * @return PBShopInfo  
	 * @date 2022年3月13日下午3:36:57
	 */
	public PBShopInfoBuilder toProto() {
		PBShopInfoBuilder builder = PBShopInfoBuilder.newInstance();
		builder.setShopId(this.getShopId());
		builder.setRefreshTime(this.getRefreshTime());
		builder.setRefreshTime(this.getRefreshNum());
		builder.addAllItemRecord(ResourceHelper.toPairProto(buyedMap));
		return builder;
	}
}
