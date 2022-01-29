package com.cat.server.game.module.playermail;

import com.cat.server.admin.module.mail.BackstageMail;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigMail;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.IMailServiceContainer;
import com.cat.server.game.module.mail.assist.MailType;
import com.cat.server.game.module.playermail.domain.PlayerMail;
import com.cat.server.game.module.playermail.domain.PlayerMailDomain;
import com.cat.server.game.module.shadow.IShadowService;
import com.cat.server.game.module.shadow.domain.Shadow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * PlayerMail控制器
 */
@Service
public class PlayerMailService implements IMailServiceContainer{
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMailService.class);
	
	@Autowired 
	private PlayerMailManager playerMailManager;
	
	@Autowired 
	private IShadowService shadowService;
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		playerMailManager.remove(playerId);
	}
	
	/////////////接口方法//////////////////

	@Override
	public int mailType() {
		return MailType.PLAYER_MAIL.getMailType();
	}
	
	@Override
	public ResultCodeData<Long> sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args) {
		// TODO 从邮件配置获取邮件信息
		ConfigMail config = ConfigManager.getInstance().getConfig(ConfigMail.class, configID);
		if(config == null) {
			return ResultCodeData.of(ErrorCode.CONFIG_NOT_EXISTS);
		}
		final String title = config.getTitle();
		final String content = config.getContent();
		final int expireDays = config.getExpiredDays();
		return this.sendMail(playerId, title, content, expireDays, rewards);
	}

	@Override
	public ResultCodeData<Long> sendMail(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		//发送邮件, 先判断是否有这个玩家
		Shadow shadow = shadowService.get(playerId);
		if (shadow == null) {
			return ResultCodeData.of(ErrorCode.MAIL_NOT_FOUND_PLAYER);
		}
		//创建邮件,保存至数据库
		PlayerMail playerMail = PlayerMail.create(playerId, title, content, expiredDays, rewards);
		//获取到邮箱, 发送邮件
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain != null) {
//			创建邮件加入玩家对象
			domain.putBean(playerMail.getId(), playerMail);
		}
		//	FIXME 是否有必要同步给玩家新邮件? 还是发送红点消息, 让玩家重新请求邮件?
		//TODO 发送红点消息, 通知客户端有新邮件
//		responsePlayerMail(playerId, Lists.newArrayList(playerMail));
		return ResultCodeData.of(ErrorCode.SUCCESS, playerMail.getId());
	}

	@Override
	public ErrorCode deleteMail(long mailId, long playerId) {
		PlayerMailDomain domain = playerMailManager.getOrLoadDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		PlayerMail mail = domain.removeBean(mailId);
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
	public IMail getMail(long mailId, long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return null;
		}
		return domain.getBean(mailId);
	}

	@Override
	public Collection<? extends IMail> getMails(long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return null;
		}
		return domain.getBeans();
	}

	/**
	 * 玩家收到后台邮件, 只要有此玩家, 不管是否在线, 都视为可以收到邮件
	 */
	@Override
	public ResultCodeData<Long> sendMail(BackstageMail backstageMail) {
		final long playerId = backstageMail.getPlayerId();
		final String title = backstageMail.getTitle();
		final String content = backstageMail.getContent();
		final int expiredDays = backstageMail.getExpireDays();
		final Map<Integer, Integer> rewards = backstageMail.getReward();
		return this.sendMail(playerId, title, content, expiredDays, rewards);
	}

}