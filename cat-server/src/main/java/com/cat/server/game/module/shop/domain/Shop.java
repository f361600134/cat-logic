package com.cat.server.game.module.shop.domain;

import java.util.HashMap;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.cat.server.game.module.shop.proto.PBShopInfoBuilder;
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
 * 1: 限制购买
 * 2: 限制购买,可以资源刷新,不限制资源刷新次数(-1)
 * 3: 限制购买,可以资源刷新,限制资源刷新次数(3次)
 * 4: 限制购买,可以资源刷新,限制资源刷新次数(3次),免费刷新次数(3次),
 * 5: 限制购买,可以资源刷新,限制资源刷新次数(3次),免费刷新次数(3次),免费次数不足时开始倒计时
 * 6: 限制购买,不可以资源刷新,不可以免费刷新,倒计时结束后刷新
 * 
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
	 * 玩家商店相关数据重置
	 */
	public void reset() {
		this.setRefreshTime(TimeUtil.now());
		this.getBuyedMap().clear();
		this.update();
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
