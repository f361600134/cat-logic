package com.cat.server.admin.module.mail;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台邮件
 * @author Jeremy
 */
class BackstageMail {

	/**
	 * 邮件类型,1个人邮件,2群邮件
	 */
	private int mailType;
	/**
	 * 邮件id, 如果是新邮件, 则为0.
	 */
	private long mailId;
	/**
	 * 玩家id
	 */
	private long playerId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String context;
	/**
	 * 过期时间(day)
	 */
	private int expireDays;
	/**
	 * 奖励
	 */
	private Map<Integer, Integer> reward = new HashMap<>();

	public int getMailType() {
		return mailType;
	}

	public void setMailType(int mailType) {
		this.mailType = mailType;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Map<Integer, Integer> getReward() {
		return reward;
	}

	public void setReward(Map<Integer, Integer> reward) {
		this.reward = reward;
	}

	public int getExpireDays() {
		return expireDays;
	}

	public void setExpireDays(int expireDays) {
		this.expireDays = expireDays;
	}

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}
	
}