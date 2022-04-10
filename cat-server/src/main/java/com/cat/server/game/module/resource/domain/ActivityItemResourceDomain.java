package com.cat.server.game.module.resource.domain;

import java.util.List;
import java.util.Map;

import com.cat.server.game.module.activityitem.domain.ActivityItem;
import com.google.common.collect.Lists;

/**
* @author Jeremy
* @deprecated 弃用活动背包
*/
public class ActivityItemResourceDomain extends AbstractResourceDomain<Long, ActivityItem>{
	
	public static final int LIMIT = 999;

	ActivityItemResourceDomain(long playerId) {
		super(playerId);
	}
	
	ActivityItemResourceDomain(long playerId, Map<Long, ActivityItem> itemMap) {
		super(playerId, itemMap);
	}
	
	@Override
	public int getTotalLimit() {
		return LIMIT;
	}

	@Override
	public int getLimit(int configId) {
		//如需要, 通过配置获取到此类物品的最大限制
		return LIMIT;
	}

	@Override
	public boolean deduct(ActivityItem item, int count) {
		try {
			if (item != null) {
				int curCount = item.deductCount(count);
				item.update();
				if (curCount > 0) 
					updateList.add(item);
				else {
					//已删除物品移除缓存
					getBeanMap().remove(item.getUniqueId());
					deleteList.add(item.getUniqueId());
				}
			}
			return true;
		} catch (Exception e) {
			log.error("deductItem error", e);
			return false;
		}
	}

	@Override
	public List<ActivityItem> add(int configId, int count) {
		//背包加入普通道具
		try {
			ActivityItem item = getBeanByConfigId(configId);
			if (count <= 0) return Lists.newArrayList(item);
			if(item == null) {//没有此物品,创建
				item = ActivityItem.create(playerId, configId, count);
				getBeanMap().put(item.getItemId(), item);
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
	
	public static ActivityItemResourceDomain create(long playerId, Map<Long, ActivityItem> beanMap) {
		ActivityItemResourceDomain domain = new ActivityItemResourceDomain(playerId, beanMap);
		return domain;
	}

	@Override
	public void beforeClearExpire(ActivityItem v) {
		// TODO Auto-generated method stub
	}
	
}