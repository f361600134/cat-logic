package com.cat.server.game.module.activityitem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.server.game.module.player.IPlayerService;

/**
 * ActivityItem控制器
 */
@Controller
public class ActivityItemController {
	
	private static final Logger log = LoggerFactory.getLogger(ActivityItemController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private ActivityItemService activityItemService;
	
	

}
