package com.cat.server.game.module.group.domain;

import com.cat.server.utils.TimeUtil;

/**
 * 进入家族申请信息
 * @auth Jeremy
 * @date 2021年5月10日下午10:16:24
 */
public class DefaultApply {

	/**
	 * 申请玩家id
	 */
	private long playerId;

	/**
	 * 申请时间
	 */
	private long createTime;

	public DefaultApply(long playerId) {
		this.playerId = playerId;
		this.createTime = TimeUtil.now();
	}
	
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 创建一条申请
	 * @param playerId
	 * @return
	 */
	public static DefaultApply create(long playerId) {
		return new DefaultApply(playerId);
	}

}
