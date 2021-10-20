package com.cat.server.game.module.shadow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.server.game.module.player.IPlayerService;

/**
 * Shadow控制器
 */
@Controller
public class ShadowController {
	
	private static final Logger log = LoggerFactory.getLogger(ShadowController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private ShadowService shadowService;
	
	
//	/*
//	*请求玩家影子数据
//	*/
//	@Cmd(value = PBProtocol.ReqShadowInfo_VALUE)
//	public void ReqShadowInfo(ISession session, ReqShadowInfo req) {
//		long playerId = session.getUserData();
//		RespShadowInfoBuilder ack = RespShadowInfoBuilder.newInstance();
//		shadowService.reqShadowInfo(playerId, req, ack);
//		playerService.sendMessage(playerId, ack);
//	}
	

}
