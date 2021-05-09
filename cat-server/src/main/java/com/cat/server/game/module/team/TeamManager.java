package com.cat.server.game.module.team;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.module.team.domain.TeamDomain;
import com.google.common.collect.Multimap;


@Component
class TeamManager implements IModuleManager<Long, TeamDomain>{
	
	/**
	 * 域缓存
	 * key:队伍id
	 * value:队伍domain
	 */
	protected final Map<Long, TeamDomain> domains = new ConcurrentHashMap<>();
	
	/**
	 * key: 队伍id
	 * value: 玩家id列表
	 */
	private Multimap<Integer, Long> teamPlayerMap;
	
	/**
	 * key: 玩家id
	 * value: 队伍id
	 */
	private Map<Long, Integer> playerTeamMap;
	
	
	@Override
	public TeamDomain getDomain(Long id) {
		return domains.get(id);
	}

	@Override
	public void remove(Long id) {
		domains.remove(id);
	}
	
	
	
	

}
