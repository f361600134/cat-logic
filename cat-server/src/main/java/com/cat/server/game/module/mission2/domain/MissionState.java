package com.cat.server.game.module.mission2.domain;

/**
 * 	任务状态
 * @author Jeremy
 */
public enum MissionState{
	/**
	 * 未完成<br>
	 * 未领取行为 -> 未完成状态
	 */
	STATE_NONE(0),
	/**
	 * 已完成<br>
	 * 完成任务行为 -> 已完成状态
	 */
	STATE_COMPLETE(1),
	/**
	 * 已領取<br>
	 * 提交任务行为 -> 已领取状态
	 */
	STATE_REWARDED(2),	
	;
	private final int value;
	private MissionState(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}