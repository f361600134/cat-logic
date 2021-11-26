package com.cat.server.game.module.groupmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.api.core.task.TokenTaskQueueExecutor;
import com.cat.server.common.ServerConfig;
import com.cat.server.game.data.proto.PBMail.PBMailInfo;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.groupmail.domain.GroupMail;
import com.cat.server.game.module.groupmail.domain.GroupMailDomain;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.IMailServiceContainer;
import com.cat.server.game.module.mail.assist.MailState;
import com.cat.server.game.module.resource.helper.ResourceHelper;


/**
 * GroupMail控制器
 * @author Jeremy
 */
@Service
class GroupMailService implements IGroupMailService, IMailServiceContainer{
	
	private static final Logger log = LoggerFactory.getLogger(GroupMailService.class);
	
	@Autowired private ServerConfig serverConfig;
	
	@Autowired private GroupMailManager groupMailManager;
	
	/**	公共的线程池处理器*/
	@Autowired private TokenTaskQueueExecutor defaultExecutor;

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
	public ErrorCode markeAsRead(long mailId, long playerId) {
		ResultCodeData<GroupMail> ret = groupMailManager.getGroupMail(serverConfig.getServerId(), mailId);
		ErrorCode errorCode = ret.getErrorCode();
		if (!errorCode.isSuccess()) {
			return ret.getErrorCode();
		}
		ret.getData().mark(playerId, MailState.READ);
		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode markAsReward(long mailId, long playerId) {
		ResultCodeData<GroupMail> ret = groupMailManager.getGroupMail(serverConfig.getServerId(), mailId);
		ErrorCode errorCode = ret.getErrorCode();
		if (!errorCode.isSuccess()) {
			return ret.getErrorCode();
		}
		ret.getData().mark(playerId, MailState.REWARD);
		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode markeForRemoval(long mailId, long playerId) {
		ResultCodeData<GroupMail> ret = groupMailManager.getGroupMail(serverConfig.getServerId(), mailId);
		ErrorCode errorCode = ret.getErrorCode();
		if (!errorCode.isSuccess()) {
			return ret.getErrorCode();
		}
		ret.getData().mark(playerId, MailState.DELETE);
		return ErrorCode.SUCCESS;
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
	public ErrorCode sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args) {
		throw new UnsupportedOperationException("不支持的操作, 群发邮件不建议使用游戏内的模板");
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
	public Map<Integer, Integer> getReward(long mailId, long playerId) {
		ResultCodeData<GroupMail> ret = groupMailManager.getGroupMail(serverConfig.getServerId(), mailId);
		ErrorCode errorCode = ret.getErrorCode();
		if (!errorCode.isSuccess()) {
			return Collections.emptyMap();
		}
		return ret.getData().getRewardMap();
	}

	@Override
	public Collection<PBMailInfo> toProto(long playerId) {
		GroupMailDomain domain = groupMailManager.getDomain(serverConfig.getServerId());
		if (domain == null) {
			log.info("sendMail error, domain is null");
			return Collections.emptyList();
		}
		List<PBMailInfo> ret = new ArrayList<>();
		for (GroupMail mail : domain.getBeans()) {
			ret.add(mail.toProto(playerId));
		}
		return ret;
	}
	
	@Override
	public int getState(long mailId, long playerId) {
		ResultCodeData<GroupMail> ret = groupMailManager.getGroupMail(serverConfig.getServerId(), mailId);
		ErrorCode errorCode = ret.getErrorCode();
		if (!errorCode.isSuccess()) {
			return 0;
		}
		return ret.getData().getState(playerId);
	}
	
	@Override
	public IMail getMail(long mailId, long playerId) {
		return groupMailManager.getGroupMail(serverConfig.getServerId(), mailId).getData();
	}

	@Override
	public Map<Integer, Integer> getAllReward(long playerId) {
		GroupMailDomain domain = groupMailManager.getDomain(serverConfig.getServerId());
		if (domain == null) {
			log.info("sendMail error, domain is null");
			return Collections.emptyMap();
		}
		Map<Integer, Integer> rewardMap = new HashMap<>();
		for (GroupMail mail : domain.getBeans()) {
			if (mail.isRewarded(playerId) || mail.isExpired()) {
				continue;
			}
			ResourceHelper.mergeToMap(mail.getRewardMap(), rewardMap);
		}
		return rewardMap;
	}

	@Override
	public Collection<PBMailInfo> toProto(long playerId, List<Long> mailIds) {
		// TODO Auto-generated method stub
		return null;
	}
}
 
 
