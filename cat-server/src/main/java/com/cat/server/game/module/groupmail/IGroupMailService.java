package com.cat.server.game.module.groupmail;

import java.util.Map;

/**
 * GroupMail接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IGroupMailService {
	
//	/**
//	 * 接收邮件, 从后台发送邮件到此模块<br>
//	 * 后台可以删掉此邮件
//	 * @param optMailId 运营后台邮件id
//	 * @param title 标题
//	 * @param content 内容
//	 * @param reward 奖励
//	 */
//	public void sendMail(long optMailId, String title, String content, int expiredDays, Map<Integer, Integer> reward);
//	
////	/**
////	 * 修改邮件, 邮件内容可以被后台修改覆盖<br>
////	 */
////	public void updateMail(long optMailId, String title, String content, Map<Integer, Integer> reward);
//	
//	/**
//	 * 删除邮件, 提供删掉邮件接口, 供后台使用<br>
//	 */
//	public void deleteMail(long mailId);
//	
//	/**
//	 * 标记为已读<br>
//	 */
//	public void markeAsRead(long playerId, long mailId);
//	
//	/**
//	 * 领取邮件奖励<br>
//	 */
//	public void reward(long playerId);
//	
//	/**
//	 * 标记为删除<br>
//	 */
//	public void markeForRemoval(long playerId);

}