package com.cat.server.game.module.playermail;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.IMailServiceContainer;
import com.cat.server.game.module.playermail.domain.PlayerMail;
import com.cat.server.game.module.playermail.domain.PlayerMailDomain;


/**
 * PlayerMail控制器
 */
@Service
public class PlayerMailService implements IMailServiceContainer{
	
//	private static final Logger log = LoggerFactory.getLogger(PlayerMailService.class);
	
	@Autowired 
	private PlayerMailManager playerMailManager;
	
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
		return PLAYER_MAIL;
	}
	
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
		return ErrorCode.SUCCESS;
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