package com.cat.server.game.module.mail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailResourceService {
	
	/**
	 * 组邮件id和个人邮件id, 可以用类型区分出来
	 */
	@Autowired private List<IMailServiceContainer> mailServiceContainer;
	
	/**
	 * 获取邮件
	 * @param mailId 邮件id
	 * @param playerId 玩家id
	 * @return IMail 邮件对象
	 * @date 2021年11月28日下午10:05:23
	 */
	public IMail getMail(long mailId, long playerId) {
		for (IMailServiceContainer mailServiceContainer : mailServiceContainer) {
			IMail mail = mailServiceContainer.getMail(mailId, playerId);
			if (mail != null) {
				return mail;
			}
		}
		return null;
	}

	/**
	 * 获取邮件列表
	 * @param playerId
	 * @return  
	 * @return Collecion<IMail>  
	 * @date 2021年11月28日下午10:05:44
	 */
	public Collection<IMail> getMails(long playerId) {
		List<IMail> rets = new ArrayList<>();
		for (IMailServiceContainer mailServiceContainer : mailServiceContainer) {
			rets.addAll(mailServiceContainer.getMails(playerId));
		}
		return rets;
	}

}
