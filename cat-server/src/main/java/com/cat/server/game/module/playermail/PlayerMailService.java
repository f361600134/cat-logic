package com.cat.server.game.module.playermail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.data.proto.PBMail.PBMailInfo;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.IMailServiceContainer;
import com.cat.server.game.module.mail.assist.MailState;
import com.cat.server.game.module.playermail.domain.PlayerMail;
import com.cat.server.game.module.playermail.domain.PlayerMailDomain;
import com.cat.server.game.module.resource.IResourceGroupService;


/**
 * PlayerMail控制器
 */
@Service
public class PlayerMailService implements IPlayerMailService, IMailServiceContainer{
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMailService.class);
	
//	@Autowired 
//	private IPlayerService playerService;
	
	@Autowired 
	private PlayerMailManager playerMailManager;
	
	@Autowired 
	private IResourceGroupService resourceGroupService;
	
	/**
	 * 登陆,  下发所有邮件
	 * 邮件模块不同于其他模块, 邮件的触发不仅仅由玩家触发,所以登录后就得发送所有邮件
	 */
	public void onLogin(long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		Collection<PlayerMail> beans = domain.getBeans();
		log.info("邮件内容:{}",beans);
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		playerMailManager.remove(playerId);
	}
	
	/////////////业务逻辑//////////////////
	
	@Override
	public ErrorCode sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args) {
		// TODO 从邮件配置获取邮件信息
		String title = "测试邮件", content = "这是一封测试邮件忽略内容";
		int expireDays = 1;
		this.sendMail(playerId, title, content, expireDays, rewards);
		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode sendMail(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		//	创建邮件加入玩家对象
		PlayerMail playerMail = PlayerMail.create(playerId, title, content, expiredDays, rewards);
		domain.putBean(playerMail.getId(), playerMail);
		//	通知玩家
//		responsePlayerMailInfo(domain, Lists.newArrayList(playerMail.getId()));
		return ErrorCode.SUCCESS;
	}

	@Override
	public int mailType() {
		return PLAYER_MAIL;
	}

	@Override
	public ErrorCode deleteMail(long mailId, long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		PlayerMail mail = domain.getBean(mailId);
		if (mail == null) {
			return ErrorCode.MAIL_NOT_FOUND;
		}
		mail.delete();
		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode updateMail(long mailId, long playerId, String title, String content, int expiredDays,
			Map<Integer, Integer> rewards) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		PlayerMail mail = domain.getBean(mailId);
		if (mail == null) {
			return ErrorCode.MAIL_NOT_FOUND;
		}
		domain.updateMail(mailId, title, content, expiredDays, rewards);
		return ErrorCode.SUCCESS;
	}


	@Override
	public Map<Integer, Integer> getReward(long mailId, long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return Collections.emptyMap();
		}
		PlayerMail mail = domain.getBean(mailId);
		if (mail == null) {
			return Collections.emptyMap();
		}
		return mail.getRewardMap();
	}

	@Override
	public IMail getMail(long mailId, long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return null;
		}
		return domain.getBean(playerId);
	}

	@Override
	public Collection<? extends IMail> getMails(long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return null;
		}
		return domain.getBeans();
	}

}