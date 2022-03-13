package com.cat.server.game.module.shop.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleMultiDomain;



/**
* ShopDomain
* @author Jeremy
*/
public class ShopDomain extends AbstractModuleMultiDomain<Long, Integer, Shop> {

	private static final Logger log = LoggerFactory.getLogger(ShopDomain.class);
	
	public ShopDomain(){
		
	}
	
	////////////业务代码////////////////////
	
	/**
	 * 获取商品购买数量
	 * @param shopId 商店id
	 * @param configId 商品id
	 * @date 2022年3月13日上午10:33:44
	 */
	public int getBuyCount(int shopId, int configId) {
		Shop shop = this.getBean(shopId);
		return shop.getBuyedMap().getOrDefault(configId, 0);
	}
	
	/**
	 * 增加商品购买数量
	 * @param shopId 商店id
	 * @param configId 商品id
	 * @param number  数量
	 * @date 2022年3月13日上午10:33:19
	 */
	public void addBuyCount(int shopId, int configId, int number) {
		Shop shop = this.getBean(shopId);
		int realNum = shop.getBuyedMap().getOrDefault(configId, 0);
		realNum += number;
		shop.getBuyedMap().put(configId, realNum);
	}
}
