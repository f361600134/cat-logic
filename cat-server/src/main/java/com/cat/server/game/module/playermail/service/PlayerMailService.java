package com.cat.server.game.module.playermail.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cat.server.game.module.player.service.IPlayerService;
import com.cat.server.game.module.playermail.domain.PlayerMail;
import com.cat.server.game.module.playermail.domain.PlayerMailDomain;
import com.cat.server.game.module.playermail.manager.PlayerMailManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;


/**
 * PlayerMail控制器
 */
@Service
public class PlayerMailService implements IPlayerMailService {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMailService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private PlayerMailManager playerMailManager;
	
	/**
	 * 登陆,  下发所有邮件
	 * 邮件模块不同于其他模块, 邮件的触发不仅仅由玩家触发,所以登录后就得发送所有邮件
	 */
	public void onLogin(long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		Collection<PlayerMail> beans = domain.getBeans();
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
	
	/**
	 * 更新信息
	 */
	public void responsePlayerMailInfos(PlayerMailDomain domain) {
		Collection<PlayerMail> beans = domain.getBeans();
		try {
			for (PlayerMail playerMail : beans) {
				//resp.addArtifactlist(playerMail.toProto());
			}
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responsePlayerMailInfo error, playerId:{}", domain.getId());
			log.error("responsePlayerMailInfo error, e:", e);
		}
	}
	
	/**
	 * 更新信息, 通知客户端新邮件
	 */
	public void responsePlayerMailInfo(PlayerMailDomain domain, List<Integer> mailIds) {
//		Collection<PlayerMail> beans = domain.getBeans();
		try {
			for (Integer mailId : mailIds) {
				PlayerMail playerMail = domain.getBean(mailId);
//				resp.addArtifactlist(playerMail.toProto());
			}
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responsePlayerMailInfo error, playerId:{}", domain.getId());
			log.error("responsePlayerMailInfo error, e:", e);
		}
	}
	
	/////////////业务逻辑//////////////////
	
	
	/////////////接口方法////////////////////////

	@Override
	public void sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args) {
		// TODO 从邮件配置获取邮件信息
		String title = "", content = "";
		int expireDays = 1;
		this.sendMail(playerId, title, content, expireDays, rewards);
	}

	@Override
	public void sendMail(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		//	创建邮件加入玩家对象
		PlayerMail playerMail = PlayerMail.create(playerId, title, content, expiredDays, rewards);
		domain.putBean(playerMail.getId(), playerMail);
		//	通知玩家
		responsePlayerMailInfo(domain, Lists.newArrayList(playerMail.getId()));
	}
	
}