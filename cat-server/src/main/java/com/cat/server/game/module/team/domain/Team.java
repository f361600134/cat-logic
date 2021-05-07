package com.cat.server.game.module.team.domain;

/**
 * 队伍类型
 * @auth Jeremy
 * @date 2021年5月6日下午10:37:12
 */
public class Team {
	
	/**
	 * 队伍id
	 */
	private long teamId;
	
	/**
	 * 队伍类型
	 */
	private int type;
	
	/**
	 * 队伍名称
	 */
	private String name;
	
	/**
	 * 队伍条件
	 * 1.等级限制, x级~y级可以加入
	 * 2.职业限制,x职业可以加入
	 * 3.密码限制,输入密码成功后匹配
	 */
	private String condition;

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
