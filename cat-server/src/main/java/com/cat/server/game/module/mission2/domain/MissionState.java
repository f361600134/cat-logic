package com.cat.server.game.module.mission2.domain;

/**
 * 	任务状态
 * @author Jeremy
 */
public enum MissionState{
	/**
	 * 失败/放弃
	 * 由玩家主动放弃任务,或每日重置取消任务导致的失败状态
	 */
	STATE_FAILD((byte)-1),
	/**
	 * 未完成<br>
	 * 未领取行为 -> 未完成状态
	 */
	STATE_NONE((byte)0),
	/**
	 * 已完成<br>
	 * 完成任务行为 -> 已完成状态
	 */
	STATE_COMPLETE((byte)1),
	/**
	 * 已領取<br>
	 * 提交任务行为 -> 已领取状态
	 */
	STATE_REWARDED((byte)2),	
	;
	
	private final byte value;
	private MissionState(byte value) {
		this.value = value;
	}
	public byte getValue() {
		return value;
	}
}