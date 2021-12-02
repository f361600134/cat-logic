package com.cat.server.game.module.groupmail.domain;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.utils.TimeUtil;



/**
* GroupMailDomain
* @author Jeremy
*/
public class GroupMailDomain extends AbstractModuleMultiDomain<Integer, Long, GroupMail> {

	private static final Logger log = LoggerFactory.getLogger(GroupMailDomain.class);
	
	public GroupMailDomain(){
	}

	////////////业务代码////////////////////
	
	/**
	 * 创建邮件
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public void createMail(String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		//创建新邮件
		GroupMail groupMail = GroupMail.create(title, content, expiredDays, rewards);
		putBean(groupMail.getId(), groupMail);
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
