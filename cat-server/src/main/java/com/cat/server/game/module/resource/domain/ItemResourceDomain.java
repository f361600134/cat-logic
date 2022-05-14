package com.cat.server.game.module.resource.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.data.config.local.ConfigItem;
import com.cat.server.game.module.item.domain.Item;
import com.cat.server.game.module.resource.event.ResourceUpdateEvent;
import com.google.common.collect.Lists;

public class ItemResourceDomain extends AbstractResourceDomain<Long, Item>{
	
	private ItemResourceDomain(long playerId) {
		super(playerId);
	}
	
	private ItemResourceDomain(long playerId, Map<Long, Item> itemMap) {
		super(playerId, itemMap);
	}
	
	public int getLimit() {
		return LIMIT;
	}
	
	/**
	 * 是否可以叠加
	 * @param configId 配置id  
	 * @return boolean  true:可叠加, false:不可叠加
	 * @date 2021年11月16日下午9:25:40
	 */
	@Override
	public boolean isStack(int configId) {
		ConfigItem configItem = ConfigManager.getInstance().getConfig(ConfigItem.class, configId);
		if (configItem == null) {
			return false;
		}
		return configItem.getStack() > 0;
	}
	
	/**
	 * 通过配置id进行消耗<br>
	 * 道具分为可叠加与不可叠加, 可叠加道具, 可以通过costByConfigId进行消耗, 不可叠加道具只能通过CostById进行消耗
	 */
	@Override
	public boolean costByConfigId(int configId, int count) {
		if (isStack(configId)) {
			//表示可叠加道具
			return super.costByConfigId(configId, count);
		}else {
			throw new UnsupportedOperationException("不可叠加的道具只能通过唯一id进行删除");
		}
	}

	@Override
	public boolean deduct(Item item, int count) {
		if (item != null) {
			int curCount = item.deductCount(count);
			item.update();
			if (curCount > 0) 
				this.updateList.add(item);
			else {
				//已删除物品移除缓存
				this.getBeanMap().remove(item.getUniqueId());
				this.deleteList.add(item.getUniqueId());
			}
		}
		return true;
	}

	@Override
	public List<Item> add(int configId, int count) {
		List<Item> items = null;
		if (this.isStack(configId)) {
			//可叠加道具
			items = this.addNormalItem(configId, count);
		} else {
			//不可叠加道具
			items = this.addUniqueItem(configId, count);
		}
		this.updateList.addAll(items);
		return items;
	}
	
	/**
	 * 添加普通可叠加道具 
	 * @param configId 配置id
	 * @param count 增加的数量
	 * @return List<Item>  返回生成的道具列表
	 * @date 2021年11月16日下午9:36:30
	 */
	private List<Item> addNormalItem(int configId, int count){
		//背包加入普通道具
		Item item = this.getBeanByConfigId(configId);
		if (count <= 0) return Lists.newArrayList(item);
		if(item == null) {//没有此物品,创建
			item = Item.create(this.playerId, configId, count);
			this.getBeanMap().put(item.getItemId(), item);
		}else {
			//有此物品,增加数量
			item.addCount(count);
			item.update();
		}
		//发送事件
		GameEventBus.getInstance().post(ResourceUpdateEvent.create(item, count));
		return Lists.newArrayList(item);
	}
	
	/**
	 * 添加唯一道具,不可叠加
	 * @param configId 配置id
	 * @param count 数量
	 * @return List<Item>  返回生成的道具列表
	 * @date 2021年11月16日下午9:36:30
	 */
	private List<Item> addUniqueItem(int configId, int count){
		//不可叠加道具
		List<Item> items = new ArrayList<>();
		Item item = null;
		for (int i = 0; i < count; i++) {
			item = Item.create(this.playerId, configId, 1);
			this.getBeanMap().put(item.getItemId(), item);
			items.add(item);
			//发送事件
			GameEventBus.getInstance().post(ResourceUpdateEvent.create(item, 1));
		}
		return items;
	}
	
	@Override
	public int getLimit(int configId) {
		return LIMIT;
	}

	@Override
	public int getTotalLimit() {
		return LIMIT;
	}

	public static ItemResourceDomain create(long playerId, Map<Long, Item> beanMap) {
		ItemResourceDomain domain = new ItemResourceDomain(playerId, beanMap);
		return domain;
	}

}
