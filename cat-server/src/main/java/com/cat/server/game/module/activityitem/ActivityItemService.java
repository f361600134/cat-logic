package com.cat.server.game.module.activityitem;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.activityitem.domain.ActivityItem;
import com.cat.server.game.module.activityitem.domain.ActivityItemDomain;
import com.cat.server.game.module.item.domain.Item;
import com.cat.server.game.module.item.domain.ItemDomain;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.resource.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


/**
 * ActivityItem控制器
 * @author Jeremy
 */
@Service
public class ActivityItemService implements IActivityItemService, IResourceService, ILifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(ActivityItemService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ActivityItemManager activityItemManager;
	
 
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		ActivityItemDomain domain = activityItemManager.getOrLoadDomain(playerId);
		Collection<ActivityItem> beans = domain.getBeans();
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		//活动道具缓存内容不释放, 用于活动结束后回收
		//activityItemManager.remove(playerId);
	}
  
	
	/**
	 * 更新信息
	 */
	public void responseActivityItemInfo(ActivityItemDomain domain) {
		Collection<ActivityItem> beans = domain.getBeans();
		try {
			for (ActivityItem activityItem : beans) {
				//resp.addArtifactlist(activityItem.toProto());
			}
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responseActivityItemInfo error, playerId:{}", domain.getId());
			log.error("responseActivityItemInfo error, e:", e);
		}
	}
	
	
	/////////////业务逻辑//////////////////
	
  
	
	/////////////接口方法////////////////////////
	
	@Override
	public int resType() {
		return ResourceType.ActivityItem.getType();
	}
	
	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkAdd(configId, value);
	}
	
	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkEnough(configId, value);
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
		if (domain == null)	return;
		//背包加入普通道具
		domain.add(configId, value);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
		if (domain == null)	return;
		domain.costByConfigId(configId, value);
	}
	
	@Override
	public void cost(long playerId, Long id, NatureEnum nEnum) {
		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		/*
		 * 道具默认有数量概念, 唯一的物品数量默认为1.
		 * 所以唯一道具,跟普通道具在扣除上面,逻辑相同,当数量为0则清除.
		 * 这里参数就默认扣除未1
		 */
		domain.costById(id, 1);
	}

	@Override
	public int getCount(long playerId, Integer configId) {
		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
		if (domain == null) {
			return 0;
		}
		return domain.getCount(configId);
	}
	
	@Override
	public void clearExpire(long playerId, int configId) {
		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		domain.clearExpire(configId);
	}
	
	@Override
	public void start() throws Throwable {
		activityItemManager.init();
	}

	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}

//	@Override
//	public void addResource(long playerId, IResource res, NatureEnum nEnum) {
//		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
//		if (domain == null) return ;
//		if (!(res instanceof ActivityItem)) {
//			return;
//		}
//		ActivityItem item = (ActivityItem)res;
//		domain.addReource(item.getUniqueId(), item);
//	}
}
 
 
