package com.cat.server.game.module.activityitem.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.game.module.item.domain.IItem; 

/**
* @author Jeremy
*/
@PO(name = "activity_item")
public class ActivityItem extends ActivityItemPo implements IItem{

	public ActivityItem() {

	}
	
	public ActivityItem(long playerId) {
		this.playerId = playerId;
	}

	@Override
	public long getUniqueId() {
		return getItemId();
	}
	
	/**
	 * 创建一个道具对象
	 */
	public static ActivityItem create(long playerId, int configId, int count) {
		ActivityItem item = new ActivityItem();
		item.setConfigId(configId);
		item.setCount(count);
		item.setPlayerId(playerId);
		item.save();
		return item;
	}

}
