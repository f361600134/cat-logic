package com.cat.server.game.module.activityoperation.learncommunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBLearnCommunity.ReqLearnCommunityInfo;
import com.cat.server.game.data.proto.PBLearnCommunity.ReqLearnCommunityReward;
import com.cat.server.game.module.activityoperation.learncommunity.proto.RespLearnCommunityInfoBuilder;
import com.cat.server.game.module.activityoperation.learncommunity.proto.RespLearnCommunityRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;

/**
 * LearnCommunity控制器
 */
@Controller
public class LearnCommunityController {
	
	private static final Logger log = LoggerFactory.getLogger(LearnCommunityController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private LearnCommunityService learnCommunityService;
	
	
	/*
	*请求研习社信息	
	*/
	@Cmd(value = PBProtocol.ReqLearnCommunityInfo_VALUE)
	public void ReqLearnCommunityInfo(ISession session, ReqLearnCommunityInfo req) {
		long playerId = session.getUserData();
		RespLearnCommunityInfoBuilder ack = RespLearnCommunityInfoBuilder.newInstance();
		learnCommunityService.reqLearnCommunityInfo(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求领取研习社奖励
	*/
	@Cmd(value = PBProtocol.ReqLearnCommunityReward_VALUE)
	public void ReqLearnCommunityReward(ISession session, ReqLearnCommunityReward req) {
		long playerId = session.getUserData();
		RespLearnCommunityRewardBuilder ack = RespLearnCommunityRewardBuilder.newInstance();
		learnCommunityService.reqLearnCommunityReward(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	

}
