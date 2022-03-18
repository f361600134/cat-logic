package com.cat.server.game.module.shop;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.type.IShopType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @author Jeremy
*/
@Component 
class ShopManager extends AbstractModuleManager<Long, ShopDomain>{
	
	private Map<Integer, IShopType> shopMap = new HashMap<>();
	
	@Autowired
	public void initShopMap(List<IShopType> shopTypes) {
		shopTypes.forEach(shopType->{
			this.shopMap.put(shopType.getShopType(), shopType);
		});
	}
	
	/**
	 * 获取商店
	 * @return
	 */
	public IShopType getShopType(int shopId) {
		return shopMap.get(shopId);
	}
	
	/**
	 * 获取所有商店
	 * @return
	 */
	public Map<Integer, IShopType> getShopMap() {
		return shopMap;
	}

}
