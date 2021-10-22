package com.cat.server.game.module.playermail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.item.proto.PBRewardInfoBuilder;
import com.cat.server.game.module.item.proto.RespRewardsBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.playermail.assist.PlayerMailConstant;
import com.cat.server.game.module.playermail.domain.PlayerMail;
import com.cat.server.game.module.playermail.domain.PlayerMailDomain;
import com.cat.server.game.module.playermail.proto.RespMailDeleteBuilder;
import com.cat.server.game.module.playermail.proto.RespMailListBuilder;
import com.cat.server.game.module.playermail.proto.RespMailReadBuilder;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * PlayerMail控制器
 */
@Service
public class PlayerMailService implements IPlayerMailService {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMailService.class);
	
	@Autowired 
	private IPlayerService playerService;
	
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
	
	/**
	 * 更新信息
	 */
	public void responsePlayerMailInfos(PlayerMailDomain domain) {
		RespMailListBuilder resp = RespMailListBuilder.newInstance(); 
		Collection<PlayerMail> beans = domain.getBeans();
		try {
			for (PlayerMail playerMail : beans) {
				resp.addMails(playerMail.toProto());
			}
			playerService.sendMessage(domain.getId(), resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responsePlayerMailInfo error, playerId:{}", domain.getId());
			log.error("responsePlayerMailInfo error, e:", e);
		}
	}
	
	/**
	 * 更新信息, 通知客户端多封新邮件
	 */
	public void responsePlayerMailInfo(PlayerMailDomain domain, List<Long> mailIds) {
		RespMailListBuilder resp = RespMailListBuilder.newInstance(); 
		try {
			for (Long mailId : mailIds) {
				PlayerMail playerMail = domain.getBean(mailId);
				resp.addMails(playerMail.toProto());
			}
			playerService.sendMessage(domain.getId(), resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responsePlayerMailInfo error, playerId:{}", domain.getId());
			log.error("responsePlayerMailInfo error, e:", e);
		}
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	 *获取邮件列表
	 */
	public void responsePlayerMailInfo(long playerId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		this.responsePlayerMailInfos(domain);
	}
	
	/***
	 * 请求阅读邮件
	 * @param playerId
	 */
	public ErrorCode read(long playerId, long mailId, RespMailReadBuilder resp) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		PlayerMail playerMail = domain.getBean(mailId);
		if(playerMail == null){
			return ErrorCode.MAIL_NOT_FOUND;
		}
		if (playerMail.getState() == PlayerMailConstant.NONE) {
			playerMail.setState(PlayerMailConstant.READ);
			playerMail.update();
		}
		responsePlayerMailInfo(domain, Lists.newArrayList(mailId));
		return ErrorCode.SUCCESS;
	}


	/***
	 * 请求领取奖励
	 * @param playerId
	 */
	public ErrorCode receive(long playerId, long mailId) {
		if (mailId < 0) {
			return receiveAll(playerId, mailId);
		}else {
			return receiveOne(playerId, mailId);
		}
	}
	
	/**
	 * 领取一封邮件
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	private ErrorCode receiveOne(long playerId, long mailId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		PlayerMail playerMail = domain.getBean(mailId);
		if(playerMail == null){
			return ErrorCode.MAIL_NOT_FOUND;
		}
		else if (playerMail.isRewarded()) {
			return ErrorCode.MAIL_ALREADY_REWARD;
		}else if (playerMail.isExpired()) {
			return ErrorCode.MAIL_ALREADY_EXPIRED;
		}
		playerMail.setState(PlayerMailConstant.REWARD);
		playerMail.update();
		//领取附件
		resourceGroupService.reward(playerId, playerMail.getRewardMap(), NatureEnum.MailReward);
		//更新状态
		this.responsePlayerMailInfo(domain, Lists.newArrayList(mailId));
		//更新奖励
		RespRewardsBuilder reweardResp = RespRewardsBuilder.newInstance();
		playerMail.getRewardMap().forEach((key, val)->{
			PBRewardInfoBuilder builder = new PBRewardInfoBuilder();
			builder.setConfigId(key);
			builder.setCount(val);
			reweardResp.addRewards(builder.build());
		});
		playerService.sendMessage(playerId, reweardResp);
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 一键领取
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	private ErrorCode receiveAll(long playerId, long mailId) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		Map<Integer, Integer> rewardMap = Maps.newHashMap();
		List<Long> mailIds = Lists.newArrayList();
		for (PlayerMail mail : domain.getBeanMap().values()) {
			if (mail == null || mail.isExpired() || mail.isRewarded())
				continue;

			//merge reward
			ResourceHelper.mergeToMap(mail.getRewardMap(), rewardMap);
			mailIds.add(mail.getId());
			
			mail.setState(PlayerMailConstant.REWARD);
			mail.update();
			
		}
		resourceGroupService.reward(playerId, rewardMap, NatureEnum.MailReward);
		//更新状态
		this.responsePlayerMailInfo(domain, mailIds);
		//更新奖励
		RespRewardsBuilder reweardResp = RespRewardsBuilder.newInstance();
		rewardMap.forEach((key, val)->{
			PBRewardInfoBuilder builder = new PBRewardInfoBuilder();
			builder.setConfigId(key);
			builder.setCount(val);
			reweardResp.addRewards(builder.build());
		});
		playerService.sendMessage(playerId, reweardResp);
		return ErrorCode.SUCCESS;
	}
	
	/***
	 * 请求删除邮件
	 * @param playerId
	 */
	public ErrorCode delete(long playerId, long mailId, RespMailDeleteBuilder resp) {
		if (mailId < 0) {
			return deleteAll(playerId, mailId, resp);
		}else {
			return deleteOne(playerId, mailId, resp);
		}
	}
	
	/**
	 * 删除一封邮件
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	private ErrorCode deleteOne(long playerId, long mailId, RespMailDeleteBuilder resp) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		PlayerMail mail = domain.getBean(mailId);
		if(mail == null){
			return ErrorCode.MAIL_NOT_FOUND;
		}
		if (mail.canDel()) {
			resp.addMailIds(mailId);
			mail.delete();
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 删除多封邮件
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	private ErrorCode deleteAll(long playerId, long mailId, RespMailDeleteBuilder resp) {
		PlayerMailDomain domain = playerMailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		List<Long> dels = new ArrayList<>();
		for (PlayerMail mail : domain.getBeanMap().values()) {
			if (mail.canDel()) {
				resp.addMailIds(mail.getId());
				dels.add(mail.getId());
				mail.delete();
			}
		}
		return ErrorCode.SUCCESS;
	}
	
	
	
	/////////////接口方法////////////////////////

	@Override
	public void sendMail(long playerId, int configID, Map<Integer, Integer> rewards, Object... args) {
		// TODO 从邮件配置获取邮件信息
		String title = "测试邮件", content = "这是一封测试邮件忽略内容";
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