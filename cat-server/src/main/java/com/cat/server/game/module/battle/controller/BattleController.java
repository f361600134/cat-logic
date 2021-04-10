package com.cat.server.game.module.battle.controller;

import com.cat.server.game.module.battle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BattleController {
	
	@Autowired
	private BattleService service;
	
	

}
