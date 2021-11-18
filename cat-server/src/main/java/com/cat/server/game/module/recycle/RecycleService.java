package com.cat.server.game.module.recycle;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigRecycle;
import com.cat.server.game.data.proto.PBRecycle.ReqResourceRecycleInfo;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.recycle.domain.Recycle;
import com.cat.server.game.module.recycle.domain.RecycleDomain;
import com.cat.server.game.module.recycle.proto.RespResourceRecycleInfoBuilder;
import com.cat.server.game.module.resource.IResourceGroupService;

/**
 * Recycle控制器
 * @author Jeremy
 */
@Service
class RecycleService implements IRecycleService {
	
	private static final Logger log = LoggerFactory.getLogger(RecycleService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private RecycleManager recycleManager;
	
	@Autowired private IResourceGroupService resourceGroupService;
 
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		RecycleDomain domain = recycleManager.getDomain(playerId);
		if (domain == null) {
			log.info("onLogin error, domain is null, playerId:{}", playerId);
			return;
		}
		this.responseRecycleInfo(domain);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		recycleManager.remove(playerId);
	}
	
	
	/**
	 * 当活动结束, 此活动相关的资源全部回收
	 * @param playerId
	 */
    public void onActivityClose(long playerId, int activityTypeId){
    	RecycleDomain domain = recycleManager.getDomain(playerId);
		if (domain == null) {
			log.info("onLogin error, domain is null, playerId:{}", playerId);
			return;
		}
		//清理移除掉的资源
		Collection<Integer> ret = domain.clearResource(activityTypeId);
		resourceGroupService.clearExpire(playerId, ret);
    }
    
    /**
	 * 当新增加资源
	 * @param playerId 玩家id
	 * @param uniqueId 唯一id
	 * @param configId 配置id
	 */
    public void onResourceAddEvent(long playerId, long uniqueId, int configId){
		//如果属于回收道具, 存档
		boolean bool = ConfigManager.getInstance().contains(ConfigRecycle.class, configId);
		if (!bool) {
			return;
		}
		RecycleDomain domain = recycleManager.getDomain(playerId);
		if (domain == null) {
			log.info("onLogin error, domain is null, playerId:{}", playerId);
			return;
		}
		domain.addRecycle(uniqueId, configId);
    }
	
	/**
	 * 更新信息
	 */
	public void responseRecycleInfo(RecycleDomain domain) {
		RespResourceRecycleInfoBuilder resp = RespResourceRecycleInfoBuilder.newInstance();
		for (Recycle recycle : domain.getBeans()) {
			resp.addResourceCycleInfo(recycle.toProto());
		}
		playerService.sendMessage(domain.getId(), resp);
	}
	
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求资源回收信息
	* @param long playerId
	* @param ReqResourceRecycleInfo req
	* @param RespResourceRecycleInfoResp ack
	*/
	public void reqResourceRecycleInfo(long playerId, ReqResourceRecycleInfo req){
		try {
			RecycleDomain domain = recycleManager.getDomain(playerId);
			if (domain == null) {
				return;
			}
			this.responseRecycleInfo(domain);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqResourceRecycleInfo error, playerId:{}", playerId);
			log.error("reqResourceRecycleInfo error, e:", e);
		}
	}

	/////////////接口方法////////////////////////
	
	@Override
	public boolean checkRecycle(long playerId, long uniqueId, int configid) {
		RecycleDomain domain = recycleManager.getDomain(playerId);
		if (domain == null) {
			//没有域, 不能回收
			return false;
		}
		//检测是否可以被回收
		return domain.checkRecycle(uniqueId, configid);
	}

	@Override
	public void doRecycle(long playerId, long uniqueId, int configid) {
		RecycleDomain domain = recycleManager.getDomain(playerId);
		if (domain == null) {
			//没有域, 不能回收
			return;
		}
		domain.doRecycle(uniqueId, configid);
	}
	
}
 
 