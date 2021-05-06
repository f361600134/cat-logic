package com.cat.server.game.module.hero;

import com.cat.server.game.module.hero.domain.Hero;

public interface IHeroService {
	
	/**
	 * 通过武将id获取武将
	 * @param heroId
	 * @return
	 */
	public Hero getHero(long playerId, long heroId);

}
