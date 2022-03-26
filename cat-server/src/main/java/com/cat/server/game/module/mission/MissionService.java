package com.cat.server.game.module.mission;

import java.util.Collection;

import com.cat.server.game.module.mission.domain.Mission;
import com.cat.server.game.module.mission.domain.MissionDomain;
import com.cat.server.game.module.mission.domain.QuestTypeData;
import com.cat.server.game.module.mission.manager.MissionManager;
import com.cat.server.game.module.mission.proto.RespMissionInfoBuilder;
import com.cat.server.game.module.mission.proto.RespMissionQuestRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.data.proto.PBMission.*;


/**
 * Mission控制器
 * @author Jeremy
 */
@Service
public class MissionService implements IMissionService, ILifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(MissionService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private MissionManager missionManager;
	
 
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		MissionDomain domain = missionManager.getDomain(playerId);
		Collection<Mission> beans = domain.getBeans();
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		missionManager.remove(playerId);
	}
  
	
	/**
	 * 更新信息
	 */
	public void responseMissionInfo(MissionDomain domain) {
		Collection<Mission> beans = domain.getBeans();
		try {
			for (Mission mission : beans) {
				//resp.addArtifactlist(mission.toProto());
			}
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			log.error("responseMissionInfo error, playerId:{}", domain.getId(), e);
		}
	}
	
	/**
	 * 获取任务类型数据
	 * @return QuestTypeData  错误码为Success时,肯定有数据
	 * @date 2022年3月26日下午2:13:37
	 */
	public QuestTypeData getQuestTypeData(long playerId, int questTypeId, boolean createIfAbsent) {
		MissionDomain domain = missionManager.getDomain(playerId);
		if (domain == null) {
			return null;
		}
		QuestTypeData questTypeData = domain.getQuestTypeData(questTypeId, createIfAbsent);
		if (questTypeData == null) {
			return null;
		}
		return questTypeData;
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求指定任务模块信息,返回任务列表
	* @param long playerId
	* @param ReqMissionInfo req
	* @param RespMissionInfoResp ack
	*/
	public ErrorCode reqMissionInfo(long playerId, ReqMissionInfo req, RespMissionInfoBuilder ack){
		try {
			MissionDomain domain = missionManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responseMissionInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqMissionInfo error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 领取任务奖励
	* @param long playerId
	* @param ReqMissionQuestReward req
	* @param RespMissionQuestRewardResp ack
	*/
	public ErrorCode reqMissionQuestReward(long playerId, ReqMissionQuestReward req, RespMissionQuestRewardBuilder ack){
		try {
			MissionDomain domain = missionManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responseMissionInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqMissionQuestReward error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
  
	
	/////////////接口方法////////////////////////
	
	@Override
	public void start() throws Throwable {
		this.missionManager.init();
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}
	
}
 
 
