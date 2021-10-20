package com.cat.server.game.module.activityitem;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.activityitem.domain.ActivityItem;
import com.cat.server.game.module.activityitem.domain.ActivityItemDomain;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResourceService;


/**
 * ActivityItem控制器
 * @author Jeremy
 */
@Service
public class ActivityItemService implements IActivityItemService , IResourceService{
	
	private static final Logger log = LoggerFactory.getLogger(ActivityItemService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ActivityItemManager activityItemManager;
	
 
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		ActivityItemDomain domain = activityItemManager.getDomain(playerId);
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
		activityItemManager.remove(playerId);
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
}
 
 
