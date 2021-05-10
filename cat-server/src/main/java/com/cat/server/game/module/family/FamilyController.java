package com.cat.server.game.module.family;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.core.executor.DisruptorStrategy;
import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.GameSession;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.IPlayerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Family控制器
 */
@Controller
public class FamilyController {
	
	private static final Logger log = LoggerFactory.getLogger(FamilyController.class);
	
	@Autowired
	private FamilyService familyService;
	
	@Autowired
	private IPlayerService playerService;
	
	public void create(GameSession session)
	{
		String familyName = "爱你一万年";
		DisruptorStrategy.get(DisruptorStrategy.COMMON).execute(()->{
			ErrorCode errorCode = this.familyService.createFamily(session.getPlayerId(), familyName);
			//	组装返回消息
			//playerService.sendMessage(playerId, protocol);
		});
		
	} 
	

}
