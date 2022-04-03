package com.cat.server.game.module.mail;

import java.util.Collection;
import java.util.Map;

import com.cat.server.admin.module.mail.BackstageMail;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.resource.domain.ResourceGroup;

/**
 * 邮件通用接口
 * @author Jeremy
 */
public interface IMailService {
	
	/**
	 * 发送一封后台邮件
	 * @param playerId 玩家id
	 * @param configID 配置id
	 * @param rewards 奖励
	 * @param args 模板参数
	 */
	public ErrorCode sendMail(BackstageMail backstageMail);
	
	/**
	 * 发送一封邮件模板内的邮件
	 * @param playerId 玩家id
	 * @param configID 配置id
	 * @param rewards 奖励
	 * @param args 模板参数
	 */
	public ErrorCode sendMail(int mailType, long playerId, int configID, ResourceGroup rewards, Object... args);
	
	/**
	 * 发送一封带文本, 奖励的邮件
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public ErrorCode sendMail(int mailType, long playerId, String title, String content, int expiredDays, ResourceGroup rewards);

	/**
	 * 根据邮件id删掉一封邮件, 真正意义上的删除, 从数据库删除
	 * @param mailId
	 */
	public ErrorCode deleteMail(int mailType, long mailId, long playerId);
	
	/**
	 * 根据邮件id修改一封邮件
	 * @param mailType 邮件类型
	 * @param mailId 邮件id
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 * @return
	 */
	public ErrorCode updateMail(int mailType, long mailId, long playerId, String title, String content, int expiredDays, ResourceGroup rewards);
	
	/**
	 * 返回一封邮件
	 */
	public IMail getMail(int mailType, long mailId, long playerId);
	
	/**
	 * 返回所有邮件
	 */
	public Collection<? extends IMail> getMails(int mailType, long playerId);

}
