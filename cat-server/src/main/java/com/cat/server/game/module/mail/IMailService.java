package com.cat.server.game.module.mail;

import java.util.Map;

import com.cat.server.game.helper.result.ErrorCode;

/**
 * 邮件通用接口
 * @author Jeremy
 */
public interface IMailService {
	
	/**
	 * 发送一封邮件模板内的邮件
	 * @param playerId 玩家id
	 * @param configID 配置id
	 * @param rewards 奖励
	 * @param args 模板参数
	 */
	public ErrorCode sendMail(int mailType, long playerId, int configID, Map<Integer, Integer> rewards, Object... args);
	
	/**
	 * 发送一封带文本, 奖励的邮件
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public ErrorCode sendMail(int mailType, long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards);

	

}
