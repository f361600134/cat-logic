package com.cat.server.game.module.activityoperation.learncommunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.event.PlayerBaseEvent;
import com.cat.server.game.data.proto.PBLearnCommunity.ReqLearnCommunityInfo;
import com.cat.server.game.data.proto.PBLearnCommunity.ReqLearnCommunityReward;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.activity.IActivityService;
import com.cat.server.game.module.activity.event.ActivityStatusUpdateEvent;
import com.cat.server.game.module.activity.status.IActivityStatus;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.cat.server.game.module.activity.type.impl.LearnCommunityActivityType;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunity;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunityDomain;
import com.cat.server.game.module.activityoperation.learncommunity.proto.RespLearnCommunityInfoBuilder;
import com.cat.server.game.module.activityoperation.learncommunity.proto.RespLearnCommunityRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;


/**
 * LearnCommunity控制器
 * @author Jeremy
 */
@Service
public class LearnCommunityService implements ILearnCommunityService{
	
	private static final Logger log = LoggerFactory.getLogger(LearnCommunityService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private LearnCommunityManager manager;
	
	@Autowired private IActivityService activityService;
	
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		//检测活动结束
		ResultCodeData<LearnCommunityActivityType> resultData = 
				activityService.getUsableActivityType(ActivityTypeEnum.LEARN_COMMUNITY, LearnCommunityActivityType.class);
		if (!resultData.isSuccess()) {
			return;
		}
		LearnCommunityDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			log.info("LearnCommunityService error, domain is null");
			return;
		}
		//检测每日重置
		domain.checkAndReset();
		//下发最新的研习社至客户端
		this.responseLearnCommunityInfo(domain);
	}
	
	/**
	 * 当玩家离线,不做处理,模块数据常驻,由活动代理处理
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		
	}
	
	/**
	 * 当收到事件, 触发任务
	 * @param event
	 */
	public void onEvent(PlayerBaseEvent event){
		long playerId = event.getPlayerId();
		//检测活动结束
		ResultCodeData<LearnCommunityActivityType> resultData = 
				activityService.getUsableActivityType(ActivityTypeEnum.LEARN_COMMUNITY, LearnCommunityActivityType.class);
		if (!resultData.isSuccess()) {
			return;
		}
		LearnCommunityDomain domain = manager.getDomain(playerId);
		if (domain == null) {
			log.info("onEvent error, playerId:{}", playerId);
			return;
		}
		domain.onProcess(event);
	}
	
	/**
	 * 当活动状态变化
	 * @param event
	 */
	public void onActivityStatusUpdate(ActivityStatusUpdateEvent event){
		ResultCodeData<LearnCommunityActivityType> resultData = 
				activityService.getUsableActivityType(event.getActivityTypeId(), LearnCommunityActivityType.class);
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
  
	/**
	 * 更新信息
	 */
	public void responseLearnCommunityInfo(LearnCommunityDomain domain) {
		LearnCommunity bean = domain.getBean();
		try {
			RespLearnCommunityInfoBuilder resp = RespLearnCommunityInfoBuilder.newInstance();
			resp.setLevel(bean.getLevel());
			resp.setExp(bean.getExp());
			resp.setExclusive(bean.getExclusive());
			
			playerService.sendMessage(domain.getId(), resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responseLearnCommunityInfo error, playerId:{}", domain.getId());
			log.error("responseLearnCommunityInfo error, e:", e);
		}
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求研习社信息	
	* @param long playerId
	* @param ReqLearnCommunityInfo req
	* @param RespLearnCommunityInfoResp ack
	*/
	public ErrorCode reqLearnCommunityInfo(long playerId, ReqLearnCommunityInfo req, RespLearnCommunityInfoBuilder ack){
		try {
			ResultCodeData<LearnCommunityActivityType> resultData = 
					activityService.getUsableActivityType(ActivityTypeEnum.LEARN_COMMUNITY, LearnCommunityActivityType.class);
			ErrorCode errorCode = resultData.getErrorCode();
			if (!errorCode.isSuccess()) {
				return errorCode;
			}
			
			LearnCommunityDomain domain = manager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			
			this.responseLearnCommunityInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqLearnCommunityInfo error, playerId:{}", playerId);
			log.error("reqLearnCommunityInfo error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求领取研习社奖励
	* @param long playerId
	* @param ReqLearnCommunityReward req
	* @param RespLearnCommunityRewardResp ack
	*/
	public ErrorCode reqLearnCommunityReward(long playerId, ReqLearnCommunityReward req, RespLearnCommunityRewardBuilder ack){
		try {
			ResultCodeData<LearnCommunityActivityType> resultData = 
					activityService.getUsableActivityType(ActivityTypeEnum.LEARN_COMMUNITY, LearnCommunityActivityType.class);
			ErrorCode errorCode = resultData.getErrorCode();
			if (!errorCode.isSuccess()) {
				return errorCode;
			}
			LearnCommunityDomain domain = manager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responseLearnCommunityInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqLearnCommunityReward error, playerId:{}", playerId);
			log.error("reqLearnCommunityReward error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}

	@Override
	public LearnCommunityActivityType getActivityType() {
		return activityService.getActivityType(activityType(), LearnCommunityActivityType.class);
	}

	@Override
	public ActivityTypeEnum activityType() {
		return ActivityTypeEnum.LEARN_COMMUNITY;
	}
	
	/////////////接口方法////////////////////////
	
}
 
 
