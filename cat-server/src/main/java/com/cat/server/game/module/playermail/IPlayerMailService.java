package com.cat.server.game.module.playermail;

import java.util.Map;

/**
 * PlayerMail接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IPlayerMailService {
	
	/**
	 * 发送一封邮件模板内的邮件
	 * @param playerId 玩家id
	 * @param configID 配置id
	 * @param rewards 奖励
	 * @param args 模板参数
	 */
	public void sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args);
	
	/**
	 * 发送一封带文本, 奖励的邮件
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public void sendMail(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards);

}