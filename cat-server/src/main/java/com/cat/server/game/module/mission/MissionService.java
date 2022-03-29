package com.cat.server.game.module.mission;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.lifecycle.Priority;
import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.data.proto.PBMission.ReqMissionInfo;
import com.cat.server.game.data.proto.PBMission.ReqMissionQuestReward;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.functioncontrol.AbstractPlayerModuleService;
import com.cat.server.game.module.mission.domain.MissionDomain;
import com.cat.server.game.module.mission.domain.QuestTypeData;
import com.cat.server.game.module.mission.handler.IQuestHandler;
import com.cat.server.game.module.mission.manager.MissionManager;
import com.cat.server.game.module.mission.proto.RespMissionInfoBuilder;
import com.cat.server.game.module.mission.proto.RespMissionQuestRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;


/**
 * Mission控制器
 * @author Jeremy
 */
@Service
public class MissionService extends AbstractPlayerModuleService<MissionDomain> implements IMissionService{
	
	private static final Logger log = LoggerFactory.getLogger(MissionService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private MissionManager missionManager;
	
	/**
	 * 更新信息
	 */
	public void responseMissionInfo(MissionDomain domain, int missionType) {
		IQuestHandler<?> handler = this.missionManager.getQuestHandler(missionType);
		RespMissionInfoBuilder builder = handler.toProto(domain.getId());
		playerService.sendMessage(domain.getId(), builder);
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
	* @param playerId 玩家id
	* @param req 请求
	*/
	public ErrorCode reqMissionInfo(long playerId, ReqMissionInfo req){
		try {
			MissionDomain domain = missionManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			this.responseMissionInfo(domain, req.getMissionId());
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqMissionInfo error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 领取任务奖励
	* @param playerId 玩家id
	* @param req 请求
	* @param ack 响应
	*/
	public ErrorCode reqMissionQuestReward(long playerId, ReqMissionQuestReward req, RespMissionQuestRewardBuilder ack){
		try {
			MissionDomain domain = missionManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			int missionType = 0;
			IQuestHandler<?> handler = this.missionManager.getQuestHandler(missionType);
			ResultCodeData<Map<Integer, Integer>> result = handler.submit(playerId, req.getId());
			return result.getErrorCode();
		} catch (Exception e) {
			log.error("reqMissionQuestReward error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	/////////////接口方法////////////////////////
	
	/**
	 * 对于任务管理类, 只负责任务逻辑的统一实现, 是否重置, 则由子类完成
	 */
	@Override
	public boolean checkAndReset(long playerId, long now) {
		for (IQuestHandler<?> handler : this.missionManager.getQuestHandlers()) {
			handler.checkAndReset(now, now, true);
		}
		return true;
	}

	@Override
	public void responseAllInfo(MissionDomain domain) {
		for (IQuestHandler<?> handler : this.missionManager.getQuestHandlers()) {
			RespMissionInfoBuilder builder = handler.toProto(domain.getId());
			playerService.sendMessage(domain.getId(), builder);
		}
	}

	@Override
	public IModuleManager<Long, MissionDomain> getModuleManager() {
		return missionManager;
	}

	@Override
	public int getModuleId() {
		return ModuleDefine.MISSION.getModuleId();
	}
	
	@Override
	public void start() throws Throwable {
		this.missionManager.init();
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}

	@Override
	public void doReset(long playerId, long now) {
		// TODO Auto-generated method stub
	}

}