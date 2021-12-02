package com.cat.server.game.module.mail;

import java.util.Collection;
import java.util.Map;

import com.cat.server.game.helper.result.ErrorCode;

/**
 * 邮件通用容器接口
 * @author Jeremy
 */
public interface IMailServiceContainer {
	
	int PLAYER_MAIL = 1;
	int GROUP_MAIL = 2;
	
	public int mailType();

	/**
	 * 发送一封邮件, 带邮件模板
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public ErrorCode sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args);
	/**
	 * 发送一封带文本, 奖励的邮件
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public ErrorCode sendMail(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards);
	
	/**
	 * 根据邮件id删掉一封邮件
	 * @param mailId
	 */
	public ErrorCode deleteMail(long mailId, long playerId);
	
	/**
	 * 根据邮件id修改一封邮件
	 * @param mailId
	 */
	public ErrorCode updateMail(long mailId, long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards);
	
	/**
	 * 返回一封邮件
	 */
	public IMail getMail(long mailId, long playerId);
	
	/**
	 * 返回所有邮件
	 */
	public Collection<? extends IMail> getMails(long playerId);

}
