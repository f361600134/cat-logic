package com.cat.server.game.module.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBActivity.ReqActivityInfo;
import com.cat.server.game.data.proto.PBActivity.ReqActivityRankReward;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.module.activity.proto.RespActivityRankRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;

/**
 * Activity控制器
 */
@Controller
public class PlayerActivityController {

	private static final Logger log = LoggerFactory.getLogger(PlayerActivityController.class);

	@Autowired private PlayerActivityService activityService;
	@Autowired private IPlayerService playerService;

	/**
	 * 查询活动状态信息
	 */
	@Cmd(value = PBProtocol.ReqActivityInfo_VALUE)
	public void reqActivityInfo(ISession session, ReqActivityInfo req) {
		long playerId = session.getUserData();
		activityService.reqActivityInfo(playerId, req);
	}
	
	/*
	*请求活动排行奖励
	*/
	@Cmd(value = PBProtocol.ReqActivityRankReward_VALUE)
	public void reqActivityRankReward(ISession session, ReqActivityRankReward req) {
		long playerId = session.getUserData();
		RespActivityRankRewardBuilder ack = RespActivityRankRewardBuilder.newInstance();
		activityService.reqActivityRankReward(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}

}
