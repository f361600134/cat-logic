package com.cat.server.game.module.player.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.GameSession;
import com.cat.net.network.controller.IController;
import com.cat.server.game.data.proto.PBLogin;
import com.cat.server.game.module.player.service.PlayerService;

@Service
public class PlayerController implements IController{
	
	private static final Logger log = LoggerFactory.getLogger(PlayerController.class);
	
	@Autowired private PlayerService playerService;
	
	@Cmd(id = 1, mustLogin = false)
	public void login(GameSession session, PBLogin.ReqLogin req) {
		log.info("PlayerHandler login， session:{}", session);
		playerService.login(session, req);
		
//		AckLoginResp ack = AckLoginResp.create();
//		session.push(ack);
	}

}
