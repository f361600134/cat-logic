package com.cat.server.admin.module.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.server.game.module.mail.IMail;
import com.cat.server.utils.TimeUtil;

/**
 * 后台邮件
 * @author Jeremy
 */
public class BackstageMail {

	/*** 邮件类型,1个人邮件,2群邮件*/
	private int mailType;
	/*** 邮件id, 如果是新邮件, 则为0.*/
	private long mailId;
	/*** 玩家id*/
	private long playerId;
	/*** 标题*/
	private String title;
	/*** 内容*/
	private String content;
	/*** 过期时间(day)*/
	private int expireDays;
	/*** 奖励 */
	private Map<Integer, Integer> reward = new HashMap<>();
	/*** 后台生成的唯一id*/
	private long backstageId;
	/*** 当前邮件生效的服务器列表*/
	private List<Integer> serverIds;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	
	public long getBackstageId() {
		return backstageId;
	}

	public void setBackstageId(long backstageId) {
		this.backstageId = backstageId;
	}

	public List<Integer> getServerIds() {
		return serverIds;
	}

	public void setServerIds(List<Integer> serverIds) {
		this.serverIds = serverIds;
	}

	public static BackstageMail create(IMail mail) {
		BackstageMail backMail = new BackstageMail();
		backMail.setMailId(mail.getId());
		backMail.setTitle(mail.getTitle());
		backMail.setContent(mail.getContent());
		backMail.setExpireDays(TimeUtil.getDifferDay(mail.getCreateTime(), mail.getExpireTime()));
		backMail.setReward(mail.getRewardMap());
		return backMail;
	}
	
}