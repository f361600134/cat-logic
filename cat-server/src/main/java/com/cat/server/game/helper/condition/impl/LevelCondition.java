package com.cat.server.game.helper.condition.impl;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.condition.ICondition;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.player.domain.PlayerContext;

/**
 * 等级条件
 * @auth Jeremy
 * @date 2022年4月6日下午9:40:39
 */
public class LevelCondition implements ICondition{
	
	private final int level;
	
	public LevelCondition(int level) {
		this.level = level;
	} 
	
	public int getLevel() {
		return level;
	}

	@Override
	public boolean accept(long playerId) {
		IPlayerService service = SpringContextHolder.getBean(IPlayerService.class);
		PlayerContext playerContext = service.getPlayerContext(playerId);
		return playerContext.getPlayer().getLevel() >= level;
	}

}
