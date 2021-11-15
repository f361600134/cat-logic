package com.cat.server.game.module.shadow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBShadow.ReqShadowInfo;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.shadow.proto.RespShadowInfoBuilder;

/**
 * Shadow控制器
 */
@Controller
public class ShadowController {
	
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private ShadowService shadowService;
	
	
	/*
	*请求玩家影子数据
	*/
	@Cmd(value = PBProtocol.ReqShadowInfo_VALUE)
	public void ReqShadowInfo(ISession session, ReqShadowInfo req) {
		long playerId = session.getUserData();
		RespShadowInfoBuilder ack = RespShadowInfoBuilder.newInstance();
		shadowService.reqShadowInfo(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	

}
