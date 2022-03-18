package com.cat.server.game.module.shop.domain;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.utils.TimeUtil;



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
	 * 获取商品列表购买数量
	 * @param shopId 商店id
	 * @return configId 商品id
	 */
	public Collection<Integer> getCommodities(int shopId) {
		Shop shop = this.getBean(shopId);
		return shop.getCommodities();
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
		shop.update();
	}
	
	/**
	 * 增加已使用资源刷新次数
	 * @param shopId 商店id
	 */
	public void addResRefreshNum(int shopId) {
		Shop shop = this.getBean(shopId);
		shop.setResRefreshNum((short)(shop.getResRefreshNum()+1));
		shop.update();
	}
	
	/**
	 * 增加已使用免费刷新次数
	 * @param shopId 商店id
	 */
	public void addFreeRefreshNum(int shopId) {
		Shop shop = this.getBean(shopId);
		shop.setFreeRefreshNum((short)(shop.getFreeRefreshNum()+1));
		if (shop.getFreeRefreshNum() > 0) {
			//刷新次数不为0, 开始恢复
			shop.setFreeRefreshTime(TimeUtil.now());
		}
		shop.update();
	}
	
//	/**
//	 * 重置商店数据
//	 * @param shopId
//	 */
//	public void init(int shopId, int maxFreeRefreshNum, int maxRedRefreshNum, Collection<Integer> commodities) {
//		Shop shop = this.getBean(shopId);
//		if (shop == null) {
//			log.info("reset error, shop:[{}] is null", shopId);
//			return;
//		}
//		shop.init(maxFreeRefreshNum, maxRedRefreshNum, commodities);
//	}
	
	/**
	 * 刷新商品列表
	 * @param shopId
	 * @param commodities
	 */
	public void refresh(int shopId, Collection<Integer> commodities) {
		Shop shop = this.getBean(shopId);
		if (shop == null) {
			log.info("reset error, shop:[{}] is null", shopId);
			return;
		}
		shop.refresh(commodities);
	}
	
	/**
	 * 重置商店数据
	 * @param shopId
	 */
	public void reset(int shopId, int maxFreeRefreshNum, int maxRedRefreshNum, Collection<Integer> commodities) {
		Shop shop = this.getBean(shopId);
		if (shop == null) {
			log.info("reset error, shop:[{}] is null", shopId);
			return;
		}
		shop.reset(maxFreeRefreshNum, maxRedRefreshNum, commodities);
	}

}
