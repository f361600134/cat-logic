package com.cat.server.game.module.user.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.controller.IController;
import com.cat.server.game.data.proto.PBLogin;

@Service
public class UserHandler implements IController{
	
	private static final Logger log = LoggerFactory.getLogger(UserHandler.class);
	
	@Cmd(id = 0, mustLogin = false)
	public void login(Object obj, PBLogin.ReqReLogin req) {
		log.info("UserHandler login");
	}

}
