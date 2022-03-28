package com.cat.server.game.module.mission;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.mission.proto.RespMissionInfoBuilder;
import com.cat.server.game.module.mission.proto.RespMissionQuestRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.helper.result.ErrorCode;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;

/**
 * Mission控制器
 */
@Controller
public class MissionController {
	
	private static final Logger log = LoggerFactory.getLogger(MissionController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private MissionService missionService;
	
	
	/*
	*请求指定任务模块信息,返回任务列表
	*/
	@Cmd(value = PBProtocol.ReqMissionInfo_VALUE)
	public void reqMissionInfo(ISession session, ReqMissionInfo req) {
		long playerId = session.getUserData();
		//RespMissionInfoBuilder ack = RespMissionInfoBuilder.newInstance();
		missionService.reqMissionInfo(playerId, req);
		//playerService.sendMessage(playerId, ack);
	}
	
	/*
	*领取任务奖励
	*/
	@Cmd(value = PBProtocol.ReqMissionQuestReward_VALUE)
	public void reqMissionQuestReward(ISession session, ReqMissionQuestReward req) {
		long playerId = session.getUserData();
		RespMissionQuestRewardBuilder ack = RespMissionQuestRewardBuilder.newInstance();
		ErrorCode code = missionService.reqMissionQuestReward(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	

}
