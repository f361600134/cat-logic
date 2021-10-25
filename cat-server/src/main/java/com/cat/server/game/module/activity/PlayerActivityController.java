package com.cat.server.game.module.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBActivity.ReqActivityInfo;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;

/**
 * Activity控制器
 */
@Controller
public class PlayerActivityController {

	private static final Logger log = LoggerFactory.getLogger(PlayerActivityController.class);

	@Autowired private PlayerActivityService activityService;

	/**
	 * 查询活动状态信息
	 */
	@Cmd(value = PBProtocol.ReqActivityInfo_VALUE)
	public void reqActivityInfo(ISession session, ReqActivityInfo req) {
		long playerId = session.getUserData();
		activityService.reqActivityInfo(playerId, req);
	}

}
