package com.cat.server.game.module.groupmail;

import java.util.Collection;
import java.util.Collections;

import org.springframework.stereotype.Component;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.groupmail.domain.GroupMail;
import com.cat.server.game.module.groupmail.domain.GroupMailDomain;

/**
* @author Jeremy
*/
@Component
public class GroupMailManager extends AbstractModuleManager<Integer, GroupMailDomain>{
	
	/**
	 * 获取邮件对象
	 * @param serverId
	 * @param mailId
	 * @return
	 */
	public ResultCodeData<GroupMail> getGroupMail(int serverId, long mailId){
		GroupMailDomain domain = getDomain(serverId);
		if (domain == null) {
			logger.info("deleteMail error, domain is null");
			return ResultCodeData.of(ErrorCode.MAIL_BOX_NOT_FOUND);
		}
		GroupMail mail = domain.getBean(mailId);
		if (mail == null) {
			return ResultCodeData.of(ErrorCode.MAIL_NOT_FOUND);
		}
		return ResultCodeData.of(ErrorCode.SUCCESS, mail);
	}
	
	/**
	 * 获取邮件对象
	 * @param serverId
	 * @param mailId
	 * @return
	 */
	public ResultCodeData<Collection<GroupMail>> getGroupMails(int serverId){
		GroupMailDomain domain = getDomain(serverId);
		if (domain == null) {
			logger.info("deleteMail error, domain is null");
			return ResultCodeData.of(ErrorCode.MAIL_BOX_NOT_FOUND, Collections.emptyList());
		}
		return ResultCodeData.of(ErrorCode.SUCCESS, domain.getBeans());
	}
	
}
