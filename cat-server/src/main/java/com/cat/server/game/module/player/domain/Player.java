package com.cat.server.game.module.player.domain;

import java.util.HashMap;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.shadow.IShadowService;

@PO(name = "player")
public class Player extends PlayerPo implements IPersistence{

	/**
	 * 属性map
	 */
	@Column(PROP_PROPERTIESTR)
	private Map<Integer, Integer> propertieMap = new HashMap<>();

	/**
	 * 检查金币是否足够
	 * 
	 * @param value
	 * @return void
	 * @date 2020年8月20日下午5:35:35
	 */
	public boolean checkProperties(int configId, int value) {
		return getProperties(configId) >= value;
	}

	/**
	 * 获取属性值
	 * 
	 * @param configId
	 * @return int
	 * @date 2020年12月20日下午5:26:37
	 */
	public int getProperties(int configId) {
		return propertieMap.getOrDefault(configId, 0);
	}

	/**
	 * 属性增加值，数值只能为正
	 * 
	 * @param configId
	 * @param added
	 * @return int
	 * @date 2020年12月20日下午5:26:57
	 */
	public void addProperties(int configId, int added) {
		int val = getProperties(configId);
		if (added < 0)
			return;
		val += added;
		val = val > Integer.MAX_VALUE ? Integer.MAX_VALUE : val;
		propertieMap.put(configId, val);
		this.update();
	}

	/**
	 * 属性减少值，数值只能为正，有函数自己实现减少操作
	 * 
	 * @param configId
	 * @param added
	 * @return int
	 * @date 2020年12月20日下午5:26:57
	 */
	public void reduceProperties(int configId, int added) {
		int val = getProperties(configId);
		if (added < 0)
			return;
		val -= added;
		val = val < 0 ? 0 : val;
		propertieMap.put(configId, val);
		this.update();
	}
	
	/**
	 * 重写玩家数据存储方法<br>
	 * 当玩家数据更新,重置影子数据,不保存,停服时保存
	 */
	@Override
	public void update() {
		IPersistence.super.update();
		IShadowService shadowService =  SpringContextHolder.getBean(IShadowService.class);
		shadowService.onPlayerUpdate(this);
	}
	
	/**
	 * 重写玩家数据存储方法<br>
	 * 当玩家数据更新,重置影子数据,不保存,停服时保存
	 */
	@Override
	public void replace() {
		IPersistence.super.replace();
		IShadowService shadowService =  SpringContextHolder.getBean(IShadowService.class);
		shadowService.onPlayerUpdate(this);
	}
	

}
