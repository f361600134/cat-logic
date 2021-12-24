package com.cat.server.game.module.groupmail.domain;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.admin.module.mail.BackstageMail;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.utils.TimeUtil;



/**
* GroupMailDomain
* @author Jeremy
*/
public class GroupMailDomain extends AbstractModuleMultiDomain<Integer, Long, GroupMail> {

	private static final Logger log = LoggerFactory.getLogger(GroupMailDomain.class);
	
	GroupMailDomain(){
	}

	////////////业务代码////////////////////
	
	/**
	 * 创建邮件
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public GroupMail createMail(BackstageMail backstageMail) {
		String title = backstageMail.getTitle();
		String content = backstageMail.getContent();
		int expiredDays = backstageMail.getExpireDays();
		long backstageId = backstageMail.getBackstageId();
		Map<Integer, Integer> rewards = backstageMail.getReward();
		List<Integer> serverIds = backstageMail.getServerIds();
		//创建新邮件
		GroupMail groupMail = GroupMail.create(title, content, expiredDays, backstageId, rewards, serverIds);
		putBean(groupMail.getId(), groupMail);
		return groupMail;
	}
	
	/**
	 * 创建邮件
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public GroupMail createMail(String title, String content, int expiredDays, long backstageId, Map<Integer, Integer> rewards, List<Integer> serverIds) {
		//创建新邮件
		GroupMail groupMail = GroupMail.create(title, content, expiredDays, backstageId, rewards, serverIds);
		putBean(groupMail.getId(), groupMail);
		return groupMail;
	}
	
	/**
	 * 修改邮件
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public void updateMail(long mailId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		GroupMail mail = getBean(mailId);
		if (mail != null) {
			mail.setTitle(title);
			mail.setContent(content);
			//计算过期时间
			long expireTime = mail.getCreateTime() + TimeUtil.DAY_MILLISECONDS * expiredDays;
			mail.setExpireTime(expireTime);
			mail.getRewardMap().clear();
			mail.getRewardMap().putAll(rewards);
			mail.update();
		}
	}
	
}
