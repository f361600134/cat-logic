package com.cat.server.game.module.activityitem;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.activityitem.domain.ActivityItem;
import com.cat.server.game.module.activityitem.domain.ActivityItemDomain;
import com.cat.server.utils.TimeUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
* @author Jeremy
*/
@Component
public class ActivityItemManager extends AbstractModuleManager<Long, ActivityItemDomain>{
	
	
	/**
	 * 活动道具, 在初始化时全部加载缓存起来
	 */
	public void init() {
		long startTime = TimeUtil.now();
		List<ActivityItem> items = process.selectAll(ActivityItem.class);
		for (ActivityItem activityItem : items) {
			long playerId = activityItem.getPlayerId();
			ActivityItemDomain domain = getOrLoadDomain(playerId);
			domain.putBean(activityItem.getItemId(), activityItem);
		}
		long costTime = TimeUtil.now() - startTime;
		if (costTime > 20) {
			logger.info("ActivityItemManager getFromDb cost time:[{}]ms", costTime);
		}
	}

}
