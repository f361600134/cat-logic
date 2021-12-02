package com.cat.server.game.module.groupmail;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.common.ServerConfig;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.groupmail.domain.GroupMail;
import com.cat.server.game.module.groupmail.domain.GroupMailDomain;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.IMailServiceContainer;


/**
 * GroupMail控制器
 * @author Jeremy
 */
@Service
class GroupMailService implements IMailServiceContainer{
	
	private static final Logger log = LoggerFactory.getLogger(GroupMailService.class);
	
	@Autowired private ServerConfig serverConfig;
	
	@Autowired private GroupMailManager groupMailManager;
	
	/////////////接口方法////////////////////////
	
	@Override
	public ErrorCode deleteMail(long mailId, long playerId) {
		ResultCodeData<GroupMail> ret = groupMailManager.getGroupMail(serverConfig.getServerId(), mailId);
		ErrorCode errorCode = ret.getErrorCode();
		if (!errorCode.isSuccess()) {
			return ret.getErrorCode();
		}
		ret.getData().delete();
		return errorCode;
	}

	@Override
	public ErrorCode sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args) {
		throw new UnsupportedOperationException("群邮件不支持配置发送");
	}

	@Override
	public int mailType() {
		return GROUP_MAIL;
	}

	@Override
	public ErrorCode sendMail(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		GroupMailDomain domain = groupMailManager.getDomain(serverConfig.getServerId());
		if (domain == null) {
			log.info("sendMail error, domain is null");
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		domain.createMail(title, content, expiredDays, rewards);
		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode updateMail(long mailId, long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		GroupMailDomain domain = groupMailManager.getDomain(serverConfig.getServerId());
		if (domain == null) {
			log.info("sendMail error, domain is null");
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		domain.updateMail(mailId, title, content, expiredDays, rewards);
		return ErrorCode.SUCCESS;
	}

	@Override
	public IMail getMail(long mailId, long playerId) {
		return groupMailManager.getGroupMail(serverConfig.getServerId(), mailId).getData();
	}

	@Override
	public Collection<? extends IMail> getMails(long playerId) {
		ResultCodeData<Collection<GroupMail>> ret = groupMailManager.getGroupMails(serverConfig.getServerId());
		if (!ret.getErrorCode().isSuccess()) {
			return Collections.emptyList();
		}
		return ret.getData();
	}

}
 
 
