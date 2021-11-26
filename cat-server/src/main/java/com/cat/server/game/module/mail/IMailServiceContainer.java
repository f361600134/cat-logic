package com.cat.server.game.module.mail;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cat.server.game.data.proto.PBMail.PBMailInfo;
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
	 * 根据邮件id删掉一封邮件
	 * @param mailId
	 */
	public ErrorCode updateMail(long mailId, long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards);

	/**
	 * 标记为已读<br>
	 */
	public ErrorCode markeAsRead(long mailId, long playerId);
	
	/**
	 * 标记为已领取奖励<br>
	 * 这里判断并返回奖励内容
	 */
	public ErrorCode markAsReward(long mailId, long playerId);
	
	/**
	 * 获取邮件奖励<br>
	 * 这里判断并返回奖励内容
	 */
	public Map<Integer, Integer> getReward(long mailId, long playerId);
	
	/**
	 * 获取邮件奖励<br>
	 * 这里判断并返回奖励内容
	 */
	public Map<Integer, Integer> getAllReward(long playerId);
	
	/**
	 * 标记为删除<br>
	 */
	public ErrorCode markeForRemoval(long mailId, long playerId);
	
	/**
	 * 返回状态, 实际上应该可以返回一个mail obj
	 * 封装一个Imail 由其他类去实现吧
	 */
	public int getState(long mailId, long playerId);
	
	/**
	 * 返回一封邮件
	 */
	public IMail getMail(long mailId, long playerId);
	
	
	/**
	 * 序列化唯proto对象<br>
	 */
	public Collection<PBMailInfo> toProto(long playerId);
	
	/**
	 * 序列化唯proto对象<br>
	 */
	public Collection<PBMailInfo> toProto(long playerId, List<Long> mailIds);

}
