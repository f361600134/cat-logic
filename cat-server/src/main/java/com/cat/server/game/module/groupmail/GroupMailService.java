package com.cat.server.game.module.groupmail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cat.server.admin.module.mail.BackstageMail;
import com.cat.server.common.ServerConfig;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.groupmail.domain.GroupMail;
import com.cat.server.game.module.groupmail.domain.GroupMailDomain;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.IMailServiceContainer;
import com.cat.server.game.module.mail.assist.MailType;
import com.cat.server.game.module.player.IPlayerService;


/**
 * GroupMail控制器
 * @author Jeremy
 */
@Service
class GroupMailService implements IMailServiceContainer{
	
	private static final Logger log = LoggerFactory.getLogger(GroupMailService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ServerConfig serverConfig;
	
	@Autowired private GroupMailManager groupMailManager;
	
	/////////////接口方法////////////////////////

	@Override
	public ResultCodeData<Long> sendMail(BackstageMail backstageMail) {
		GroupMailDomain domain = groupMailManager.getDomain(serverConfig.getServerId());
		if (domain == null) {
			log.info("sendMail error, domain is null");
			return ResultCodeData.of(ErrorCode.MAIL_BOX_NOT_FOUND);
		}
		GroupMail mail = domain.createMail(backstageMail);
		//TODO 通知所有玩家红点
		return ResultCodeData.of(ErrorCode.SUCCESS, mail.getId());
	}
	
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
	public ResultCodeData<Long> sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args) {
		throw new UnsupportedOperationException("群邮件不支持配置发送");
	}

	@Override
	public int mailType() {
		return MailType.GROUP_MAIL.getMailType();
	}

	@Override
	public ResultCodeData<Long> sendMail(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		throw new UnsupportedOperationException("群邮件不支持配置发送");
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
		return groupMailManager.getGroupMails(serverConfig.getServerId()).getData();
	}
	
	public static void main(String[] args) {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(10001, 1);
		System.out.println(JSON.toJSON(map));
	}
}