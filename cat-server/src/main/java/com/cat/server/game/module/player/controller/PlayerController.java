package com.cat.server.game.module.player.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.GameSession;
import com.cat.net.network.controller.IController;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBLogin;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.proto.AckLoginResp;
import com.cat.server.game.module.player.service.PlayerService;

@Controller
public class PlayerController implements IController{
	
	private static final Logger log = LoggerFactory.getLogger(PlayerController.class);
	
	@Autowired private PlayerService playerService;
	
	@Cmd(value = PBProtocol.ReqLogin_VALUE, mustLogin = false)
	public void login(GameSession session, PBLogin.ReqLogin req) {
		
		AckLoginResp ack = AckLoginResp.create();
		ErrorCode errorCode = playerService.login(session, req, ack);
		ack.setCode(errorCode.getCode());
		session.push(ack);
	}
	
}
