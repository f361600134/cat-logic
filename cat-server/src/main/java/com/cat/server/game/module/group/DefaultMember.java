package com.cat.server.game.module.group;

public class DefaultMember implements IMember{
	
	/**
	 * 玩家id
	 */
	private long playerId;
	
	/**
	 * 职位, 可以根据职位获取到权限
	 */
	private int position;
	
	public DefaultMember(long playerId) {
		this.playerId = playerId;
		this.position = 0;//表示默认职位
	}

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

	@Override
	public long getId() {
		return playerId;
	}
	
}
