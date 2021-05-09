package com.cat.server.game.module.team.domain;

/**
 * 队伍域
 * @auth Jeremy
 * @date 2021年5月6日下午10:28:48
 */
public class TeamDomain {
	
	//队伍id->玩家id列表
	//玩家id->队伍id
	private Team team;

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
}
