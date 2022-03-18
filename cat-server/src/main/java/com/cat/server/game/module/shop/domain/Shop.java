package com.cat.server.game.module.shop.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.helper.restore.RestorableValue;
import com.cat.server.game.module.shop.restore.ShopFreeNumberRestore;
import com.cat.server.utils.TimeUtil; 

/**
 * 
 * 这里实现的是简单的商店, 只有限购商品, 每日重置
 * 
 * refreshNumber为商店手动刷新次数, refreshTime为商店刷新时间
 * 有以下几种商店类型, 较为复杂先不实现
 * 1: 固定商店, 每日重置, 配置多少显示多少, 仅限制购买数量
 * 2: 英雄商店, 分解资源获得的部分稀少资源, 达到数量后, 可进行兑换, 可根据此资源刷新此商店.
 * 3: 积分商店, 不同积分刷新不同的资源, 比如工会, 副本, 竞技场, 段位产出的积分/资源, 在此商店进行购买.
 * 4: 符文商店, 符文拆解后, 获得的稀少资源, 达到数量后进行兑换, 免费刷新次数3次, 每天最多刷新20次
 * 5: 随机商店, 每次刷新出来若干个, 可手动刷新, 每次刷新需要消耗资源
 * 6: 神秘商店, 每次随机刷新出若干个, 不可手动刷新, 倒计时结束后刷新
 * 
 * 1: 限制购买---商品列表读表
 * 2: 限制购买,可以资源刷新,不限制资源刷新次数(-1)  ---商品列表随机且存储
 * 3: 限制购买,可以资源刷新,限制资源刷新次数(3次)   ---商品列表随机且存储
 * 4: 限制购买,可以资源刷新,限制资源刷新次数(3次),免费刷新次数(3次), ---商品列表随机且存储
 * 5: 限制购买,可以资源刷新,限制资源刷新次数(3次),免费刷新次数(3次),免费次数不足时开始倒计时 ---商品列表随机且存储
 * 6: 限制购买,不可以资源刷新,不可以免费刷新,倒计时结束后刷新 ---商品列表随机且存储
 * 
* @author Jeremy
*/
@PO(name = "shop")
public class Shop extends ShopPo implements RestorableValue, IPersistence{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5393733170885176108L;

	/**
	 * 已购买列表
	 */
	@Column(value = PROP_BUYEDSTR, clazzType = HashMap.class)
	private Map<Integer, Integer> buyedMap;
	
	/**
	 * 商品列表
	 */
	@Column(value = PROP_COMMODITIESSTR, clazzType = ArrayList.class)
	private List<Integer> commodities;
	
	/**
	 * 免费次数恢复器
	 */
	private final transient ShopFreeNumberRestore freeNumberRestore = new ShopFreeNumberRestore(this);

	public Shop() {
		
	}
	
	public Shop(long playerId) {
		this.playerId = playerId;
	}

	public Map<Integer, Integer> getBuyedMap() {
		return buyedMap;
	}
	
	List<Integer> getCommodities() {
		return commodities;
	}
	
	ShopFreeNumberRestore getFreeNumberRestore() {
		return freeNumberRestore;
	}
	
//	/**
//	 * 玩家商店初始化
//	 * @param 新商品列表
//	 */
//	public void init(int maxFreeRefreshNum, int maxRedRefreshNum, Collection<Integer> newCommodities) {
//		this.setFreeRefreshNum((short)maxFreeRefreshNum);
//		this.setResRefreshNum((short)maxRedRefreshNum);
//		this.getBuyedMap().clear();
//		this.commodities.clear();
//		this.commodities.addAll(newCommodities);
//		this.update();
//	}
	
	/**
	 * 玩家商店重置<br>
	 * 每日重置时, 重置所有数据
	 * @param 新商品列表
	 */
	public void reset(int maxFreeRefreshNum, int maxRedRefreshNum, Collection<Integer> newCommodities) {
		this.setFreeRefreshNum((short)maxFreeRefreshNum);
		this.setResRefreshNum((short)maxRedRefreshNum);
		this.getBuyedMap().clear();
		this.commodities.clear();
		this.commodities.addAll(newCommodities);
		this.setResetTime(TimeUtil.now());
		this.update();
	}
	
	/**
	 * 玩家商店刷新<br>
	 * 不管是自动刷新, 还是每日刷新, 仅刷新成新的商品列表, 清除掉商店购买记录
	 * @param 新商品列表
	 */
	public void refresh(Collection<Integer> newCommodities) {
		this.getBuyedMap().clear();
		this.commodities.clear();
		this.commodities.addAll(newCommodities);
		this.update();
	}

	@Override
	public int getNum() {
		return this.getFreeRefreshNum();
	}

	@Override
	public void setNum(int num) {
		this.setFreeRefreshNum((short)num);
	}

	@Override
	public long getLastTime() {
		return this.getFreeRefreshTime();
	}

	@Override
	public void setLastTime(long lastTime) {
		this.setFreeRefreshTime(lastTime);
	}
	
}
