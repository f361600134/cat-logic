package com.cat.server.game.module.rank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.cat.server.game.module.rank.service.RankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rank控制器
 */
@Controller
public class RankController {
	
	private static final Logger log = LoggerFactory.getLogger(RankController.class);
	
	@Autowired
	private RankService rankService;
	
	
	

}
