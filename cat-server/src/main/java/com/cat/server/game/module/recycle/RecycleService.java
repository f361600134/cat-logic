package com.cat.server.game.module.recycle;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigRecycle;
import com.cat.server.game.data.proto.PBRecycle.ReqResourceRecycleInfo;
import com.cat.server.game.module.mail.IMailService;
import com.cat.server.game.module.mail.assist.MailTemplate;
import com.cat.server.game.module.mail.assist.MailType;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.recycle.domain.Recycle;
import com.cat.server.game.module.recycle.domain.RecycleDomain;
import com.cat.server.game.module.recycle.proto.RespResourceRecycleInfoBuilder;
import com.cat.server.game.module.recycle.strategy.impl.ActivityRecycleStrategy;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	
	@Autowired private IMailService mailService;
 
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
	 * @param playerId 玩家id
	 *  @param activityTypeId 活动类型id
	 */
    public void onActivityClose(long playerId, int activityTypeId){
    	RecycleDomain domain = recycleManager.getDomain(playerId);
		if (domain == null) {
			log.info("onLogin error, domain is null, playerId:{}", playerId);
			return;
		}
		//清理移除掉的资源
		Collection<Integer> removeRes = domain.clearResource(activityTypeId);
		if (removeRes.isEmpty()) {
			return;
		}
		//回收资源转化
		Map<Integer, Integer> reward = new HashMap<>();
		for (Integer configId : removeRes) {
			ConfigRecycle config = ConfigManager.getInstance().getConfig(ConfigRecycle.class, configId);
			if (config == null) {
				continue;
			}
			int number = resourceGroupService.getCount(playerId, configId);
			int realNum = ResourceHelper.percentage(number, config.getResRate());
			reward.put(config.getResId(), realNum);
		}
		//邮件通知
		mailService.sendMail(MailType.PLAYER_MAIL.getMailType(), playerId, MailTemplate.ACTIVITY_ITEM_RECYCLE.getMailConfigId(), reward);
		//清空背包
		resourceGroupService.clearExpire(playerId, removeRes);
		this.responseRecycleInfo(domain);
    }

	/**
	 * 当活动结束,回收模块检测是否有可回收资源
	 *  @param activityTypeId 活动类型id
	 */
	public void onActivityClose(int activityTypeId){
		//统计相关资源
		Set<Integer> configIds = ConfigManager.getInstance().getConfigs(ConfigRecycle.class, c->
				(c.getStrategy() instanceof ActivityRecycleStrategy)
						&& ((ActivityRecycleStrategy)(c.getStrategy())).getActivityTypeId() == activityTypeId)
				.keySet();
		//获取指定活动玩家列表,进行回收


	}
    
    /**
     * 检测邮件回收
     */
    public void checkItemRecycle() {
    	
    }
    
    /**
	 * 当新增加资源
	 * @param playerId 玩家id
	 * @param uniqueId 唯一id
	 * @param configId 配置id
	 */
    public void onResourceAddEvent(long playerId, long uniqueId, int configId, int number){
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
		domain.updateRecycle(uniqueId, configId, number);
		this.responseRecycleInfo(domain);
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
	* @param playerId 玩家id
	* @param req 请求消息
	*/
	public void reqResourceRecycleInfo(long playerId, ReqResourceRecycleInfo req){
		try {
			RecycleDomain domain = recycleManager.getDomain(playerId);
			if (domain == null) {
				return;
			}
			//清理过期资源
			domain.clearResource();
			//下发客户端
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
		boolean bool = domain.doRecycle(uniqueId, configid);
		if (bool) {
			this.responseRecycleInfo(domain);
		}
	}
	
}