package com.cat.server.game.module.playermail.controller;

import java.util.List;
import java.util.Map;

import com.cat.server.game.module.playermail.service.PlayerMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PlayerMail控制器
 */
@Controller
public class PlayerMailController {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMailController.class);
	
	@Autowired
	private PlayerMailService playerMailService;
	
	
	

}
