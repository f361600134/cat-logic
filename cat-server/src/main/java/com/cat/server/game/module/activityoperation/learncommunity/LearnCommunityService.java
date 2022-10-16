package com.cat.server.game.module.activityoperation.learncommunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.data.proto.PBLearnCommunity.ReqLearnCommunityInfo;
import com.cat.server.game.data.proto.PBLearnCommunity.ReqLearnCommunityReward;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.activity.event.ActivityStatusUpdateEvent;
import com.cat.server.game.module.activity.status.IActivityStatus;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.cat.server.game.module.activity.type.IPlayerActivityService;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunity;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunityDomain;
import com.cat.server.game.module.activityoperation.learncommunity.proto.RespLearnCommunityInfoBuilder;
import com.cat.server.game.module.activityoperation.learncommunity.proto.RespLearnCommunityRewardBuilder;
import com.cat.server.game.module.mission.domain.QuestTypeData;
import com.cat.server.game.module.player.IPlayerService;


/**
 * LearnCommunity控制器
 * @author Jeremy
 */
@Service
public class LearnCommunityService implements ILearnCommunityService, IPlayerActivityService<LearnCommunityActivityType>{
	
	private static final Logger log = LoggerFactory.getLogger(LearnCommunityService.class);
	
	@Autowired private IPlayerService playerService;
	@Autowired private LearnCommunityManager manager;
	
	/**
	 * 登陆
	 */
	@Override
	public void onLogin(long playerId, long now) {
		//检测活动结束
		ResultCodeData<LearnCommunityActivityType> resultData = this.getUseableActivity();
		if (!resultData.isSuccess()) {
			return;
		}
		ILearnCommunityService.super.onLogin(playerId, now);
	}
	
	@Override
	public void doReset(long playerId, long now) {
		LearnCommunityDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			log.info("LearnCommunityService error, domain is null");
			return;
		}
		//检测每日重置
		domain.doReset();
	}
	
	@Override
	public void responseModuleInfo(long playerId) {
		LearnCommunityDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			log.info("LearnCommunityService error, domain is null");
			return;
		}
		LearnCommunity bean = domain.getBean();
		RespLearnCommunityInfoBuilder resp = RespLearnCommunityInfoBuilder.newInstance();
		resp.setLevel(bean.getLevel());
		resp.setExp(bean.getExp());
		resp.setExclusive(bean.getExclusive());
		playerService.sendMessage(domain.getId(), resp);
	}
	
	/**
	 * 当活动状态变化
	 * @param event
	 */
	public void onActivityStatusUpdate(ActivityStatusUpdateEvent event){
		ResultCodeData<LearnCommunityActivityType> resultData = this.getUseableActivity(event.getActivityTypeId());
		if (!resultData.isSuccess()) {
			return;
		}
		if (event.getStatus() == IActivityStatus.CLOSE) {
			//研习社活动结束, 计算奖励, 发送邮件
			onActivityClose();
		} 
	}
	
	/**
	 * 当活动结束
	 * //1. 发送邮件领取未领取的奖励
	 * //2. 清除当前研习社所有数据,保存
	 */
	public void onActivityClose() {
		//邮件通知
		for (LearnCommunityDomain domain : manager.getAllDomain()) {
			//LearnCommunity bean = domain.getBean();
		}
		//清理数据
//		domain.clear();
	}
  
	/////////////业务逻辑//////////////////
	
	/**
	* 请求研习社信息	
	* @param long playerId
	* @param ReqLearnCommunityInfo req
	* @param RespLearnCommunityInfoResp ack
	*/
	public ErrorCode reqLearnCommunityInfo(long playerId, ReqLearnCommunityInfo req, RespLearnCommunityInfoBuilder ack){
		ResultCodeData<LearnCommunityActivityType> resultData = this.getUseableActivity();
		ErrorCode errorCode = resultData.getErrorCode();
		if (!errorCode.isSuccess()) {
			return errorCode;
		}
		LearnCommunityDomain domain = manager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		this.responseModuleInfo(playerId);
		return ErrorCode.SUCCESS;
	}
	/**
	* 请求领取研习社奖励
	* @param long playerId
	* @param ReqLearnCommunityReward req
	* @param RespLearnCommunityRewardResp ack
	*/
	public ErrorCode reqLearnCommunityReward(long playerId, ReqLearnCommunityReward req, RespLearnCommunityRewardBuilder ack){
		ResultCodeData<LearnCommunityActivityType> resultData = this.getUseableActivity();
		ErrorCode errorCode = resultData.getErrorCode();
		if (!errorCode.isSuccess()) {
			return errorCode;
		}
		LearnCommunityDomain domain = manager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		this.responseModuleInfo(playerId);
		return ErrorCode.SUCCESS;
	}
	/////////////接口方法////////////////////////
	
	@Override
	public QuestTypeData getQuestTypeData(long playerId) {
		LearnCommunityDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			log.info("LearnCommunityService error, domain is null");
			return null;
		}
		LearnCommunity learnCommunity = domain.getBean();
		return learnCommunity.getQuestTypeData();
	}

	@Override
	public LearnCommunity getLearnCommunity(long playerId) {
		LearnCommunityDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			log.info("LearnCommunityService error, domain is null");
			return null;
		}
		return domain.getBean();
	}

	@Override
	public int getModuleId() {
		return ModuleDefine.LEARNCOMMUNITY.getModuleId();
	}

	@Override
	public IModuleManager<Long, ?> getModuleManager() {
		return manager;
	}

	@Override
	public ActivityTypeEnum activityType() {
		return ActivityTypeEnum.LEARN_COMMUNITY;
	}

	@Override
	public Class<LearnCommunityActivityType> activityClazz() {
		return LearnCommunityActivityType.class;
	}
}
