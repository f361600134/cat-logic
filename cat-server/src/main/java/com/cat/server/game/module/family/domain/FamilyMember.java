package com.cat.server.game.module.family.domain;

/**
 * 家族成员对象
 * @auth Jeremy
 * @date 2021年5月10日下午10:16:24
 */
public class FamilyMember {

	/**
	 * 玩家id
	 */
	private long playerId;
	
	/**
	 * 职位, 可以根据职位获取到权限
	 */
	private int position;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
}
