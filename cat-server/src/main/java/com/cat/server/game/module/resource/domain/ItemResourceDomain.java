package com.cat.server.game.module.resource.domain;

import java.util.List;
import java.util.Map;

import com.cat.server.game.module.item.domain.Item;
import com.google.common.collect.Lists;

public class ItemResourceDomain extends AbstractResourceDomain<Long, Item>{
	
	public static final int LIMIT = 999;
	
	private ItemResourceDomain(long playerId) {
		super(playerId);
	}
	
	public int getLimit() {
		return LIMIT;
	}

	@Override
	public boolean deduct(Item item, int count) {
		try {
			if (item != null) {
				int curCount = item.deductCount(count);
				item.update();
				if (curCount > 0) 
					updateList.add(item);
				else {
					//已删除物品移除缓存
					beanMap.remove(item.getUniqueId());
					deleteList.add(item);
				}
			}
			return true;
		} catch (Exception e) {
			log.error("deductItem error", e);
			return false;
		}
	}

	@Override
	public List<Item> add(int configId, int count) {
		//背包加入普通道具
		try {
			Item item = getBeanByConfigId(configId);
			if (count <= 0) return Lists.newArrayList(item);
			if(item == null) {//没有此物品,创建
				item = Item.create(playerId, configId, count);
				beanMap.put(item.getItemId(), item);
				item.save();
			}else {
				//有此物品,增加数量
				item.addCount(count);
				item.update();
			}
			updateList.add(item);
			return Lists.newArrayList(item);
		} catch (Exception e) {
			log.error("addItem error", e);
		}
		return null;
	}
	
	public static ItemResourceDomain create(long playerId, Map<Long, Item> beanMap) {
		ItemResourceDomain domain = new ItemResourceDomain(playerId);
		domain.setBeanMap(beanMap);
		return domain;
	}

	@Override
	public int getLimit(int configId) {
		return LIMIT;
	}

	@Override
	public int getTotalLimit() {
		return LIMIT;
	}

}
