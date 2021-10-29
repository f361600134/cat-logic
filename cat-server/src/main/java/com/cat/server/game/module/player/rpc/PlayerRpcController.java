package com.cat.server.game.module.player.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.cat.api.ProtocolId;
import com.cat.api.module.battle.request.ReqKickUpPlayer;
import com.cat.api.module.battle.response.RespKickUpPlayer;
import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IRpcController;

@Controller
public class PlayerRpcController implements IRpcController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Rpc(value= ProtocolId.ReqKickUpPlayer, isAuth = false)
	public void reqIdentityAuthenticate(ISession session, ReqKickUpPlayer req) {
		long playerId = req.getPlayerId();
		RespKickUpPlayer resp = new RespKickUpPlayer();
		resp.setCode(0);
		resp.setSeq(req.getSeq());
		session.push(resp);
		
		logger.info("收到RPC踢玩家下线, 玩家id:{}, 原因:{}, 返回结果:{}", playerId, req.getReason(), 0);
	}

}
