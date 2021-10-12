package com.cat.server.game.module.activityoperation.learncommunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.data.proto.PBLearnCommunity.ReqLearnCommunityInfo;
import com.cat.server.game.data.proto.PBLearnCommunity.ReqLearnCommunityReward;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunityDomain;
import com.cat.server.game.module.activityoperation.learncommunity.proto.RespLearnCommunityInfoBuilder;
import com.cat.server.game.module.activityoperation.learncommunity.proto.RespLearnCommunityRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;


/**
 * LearnCommunity控制器
 * @author Jeremy
 */
@Service
public class LearnCommunityService implements ILearnCommunityService {
	
	private static final Logger log = LoggerFactory.getLogger(LearnCommunityService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private LearnCommunityManager learnCommunityManager;
	
 
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		LearnCommunityDomain domain = learnCommunityManager.getDomain(playerId);
//		Collection<LearnCommunity> beans = domain.getBeans();
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		learnCommunityManager.remove(playerId);
	}
  
	
	/**
	 * 更新信息
	 */
	public void responseLearnCommunityInfo(LearnCommunityDomain domain) {
//		Collection<LearnCommunity> beans = domain.getBeans();
//		try {
//			for (LearnCommunity learnCommunity : beans) {
//				//resp.addArtifactlist(learnCommunity.toProto());
//			}
//			//playerService.sendMessage(playerId, resp);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("responseLearnCommunityInfo error, playerId:{}", domain.getId());
//			log.error("responseLearnCommunityInfo error, e:", e);
//		}
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
			LearnCommunityDomain domain = learnCommunityManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
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
			LearnCommunityDomain domain = learnCommunityManager.getDomain(playerId);
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
  
	
	/////////////接口方法////////////////////////
	
}
 
 
