package com.cat.server.game.module.playermail.domain;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.utils.TimeUtil;


/**
* PlayerMailDomain, 
* @author Jeremy
*/
public class PlayerMailDomain extends AbstractModuleMultiDomain<Long, Long, PlayerMail> {

	private static final Logger log = LoggerFactory.getLogger(PlayerMailDomain.class);
	
	
	public PlayerMailDomain(){
		
	}

	
	////////////业务代码////////////////////
	
	/**
	 * 修改邮件
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励
	 */
	public void updateMail(long mailId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		PlayerMail mail = getBean(mailId);
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

