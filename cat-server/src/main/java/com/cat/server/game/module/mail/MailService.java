package com.cat.server.game.module.mail;

import com.cat.server.admin.module.mail.BackstageMail;
import com.cat.server.game.data.proto.PBMail.ReqMailDelete;
import com.cat.server.game.data.proto.PBMail.ReqMailRead;
import com.cat.server.game.data.proto.PBMail.ReqMailReward;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.mail.assist.MailState;
import com.cat.server.game.module.mail.proto.RespMailDeleteBuilder;
import com.cat.server.game.module.mail.proto.RespMailListBuilder;
import com.cat.server.game.module.mail.proto.RespMailReadBuilder;
import com.cat.server.game.module.mail.proto.RespMailRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Mail控制器
 */
@Service
class MailService implements IMailService {
	
	private static final Logger log = LoggerFactory.getLogger(MailService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private IResourceGroupService resourceGroupService;
	
	@Autowired private MailManager mailManager;
	
	/**
	 * 登陆,  下发所有邮件
	 * 邮件模块不同于其他模块, 邮件的触发不仅仅由玩家触发,所以登录后就得发送所有邮件
	 */
	public void onLogin(long playerId) {
//		this.responseMailInfos(playerId);
	}
	
	/**
	 * 更新信息
	 */
	public void responseMailInfos(long playerId) {
		RespMailListBuilder resp = RespMailListBuilder.newInstance(); 
		Collection<IMail> beans = mailManager.getMails(playerId);
		try {
			for (IMail mail : beans) {
				resp.addMails(mail.toProto(playerId));
			}
			playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responsePlayerMailInfo error, playerId:{}", playerId);
			log.error("responsePlayerMailInfo error, e:", e);
		}
		playerService.sendMessage(playerId, resp);
	}
	
	/**
	 * 更新信息, 通知客户端多封新邮件
	 */
	public void responseMailInfo(long playerId, List<Long> mailIds) {
		RespMailListBuilder resp = RespMailListBuilder.newInstance(); 
		try {
			IMail mail = null;
			for (Long mailId : mailIds) {
				mail = mailManager.getMail(mailId, playerId);
				if (mail == null) {
					continue;
				}
				resp.addMails(mail.toProto(playerId));
			}
			playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responsePlayerMailInfo error, playerId:{}", playerId);
			log.error("responsePlayerMailInfo error, e:", e);
		}
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	 *获取邮件列表
	 */
	public void responseMailInfo(long playerId) {
		this.responseMailInfos(playerId);
	}
	
	/**
	* 请求邮件列表
	* @param long playerId
	* @param ReqMailList req
	* @param RespMailListResp ack
	*/
	public void reqMailList(long playerId){
		this.responseMailInfo(playerId);
	}
	
	/***
	 * 请求阅读邮件
	 * @param playerId
	 */
	public ErrorCode reqMailRead(long playerId, ReqMailRead req, RespMailReadBuilder resp) {
		final long mailId = req.getMailId();
		IMail mail = mailManager.getMail(mailId, playerId);
		if (mail == null) {
			return ErrorCode.MAIL_NOT_FOUND;
		}
		if (mail.getState(playerId) != MailState.NONE.getState()) {
			mail.addState(playerId, MailState.READ);
			mail.update();
		}
		return ErrorCode.SUCCESS;
	}

	
	/**
	* 获取邮件附件
	* @param long playerId 玩家id
	* @param ReqMailReward req 请求消息
	* @param RespMailRewardResp ack 响应消息
	*/
	public ErrorCode reqMailReward(long playerId, ReqMailReward req, RespMailRewardBuilder ack){
		final long mailId = req.getMailId(); 
		if (mailId < 0) {
			return receiveAll(playerId, mailId, ack);
		}else {
			return receiveOne(playerId, mailId, ack);
		}
	}

	/**
	 * 领取一封邮件
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	private ErrorCode receiveOne(long playerId, long mailId, RespMailRewardBuilder ack) {
		IMail mail = mailManager.getMail(mailId, playerId);
		if (mail == null) {
			return ErrorCode.MAIL_NOT_FOUND;
		}
		if (mail.getState(playerId) == MailState.REWARD.getState()) {
			return ErrorCode.MAIL_ALREADY_REWARD;
		}else if (mail.isExpired()) {
			return ErrorCode.MAIL_ALREADY_REWARD;
		}
		mail.addState(playerId, MailState.REWARD);
		mail.update();
		//领取附件
		resourceGroupService.reward(playerId, mail.getRewardMap(), NatureEnum.MailReward);
		//更新状态
		this.responseMailInfo(playerId, Lists.newArrayList(mailId));
		ack.addAllRewards(mail.getRewardMap().toCollPairProto());
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 一键领取
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	private ErrorCode receiveAll(long playerId, long mailId, RespMailRewardBuilder ack) {
		List<Long> mailIds = new ArrayList<>();
		ResourceGroup group = new ResourceGroup();
		Collection<IMail> cols = mailManager.getMails(playerId);
		for (IMail mail : cols) {
			if (mail == null || mail.isExpired() || mail.isRewarded(playerId))
				continue;

			//merge reward
			//ResourceHelper.mergeToMap(mail.getRewardMap(), rewardMap);
			group.merge(mail.getRewardMap());
			mailIds.add(mail.getId());
			
			mail.addState(playerId, MailState.REWARD);
			mail.update();
		}
		//没有可领取奖励
		if (group.empty()) {
			return ErrorCode.MAIL_ALREADY_NO_REWARD;
		}
		//奖励入背包
		resourceGroupService.reward(playerId, group, NatureEnum.MailReward);
		//更新状态
		this.responseMailInfo(playerId, mailIds);
		//更新奖励
		ack.addAllRewards(group.toCollPairProto());
		return ErrorCode.SUCCESS;
	}
	
	/***
	 * 请求删除邮件
	 * @param playerId
	 */
	public ErrorCode reqMailDelete(long playerId, ReqMailDelete req, RespMailDeleteBuilder resp) {
		final long mailId = req.getMailId();
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
		IMail mail = mailManager.getMail(mailId, playerId);
		if (mail == null) {
			return ErrorCode.MAIL_NOT_FOUND;
		}
		if (mail.canDel(playerId)) {
			resp.addMailIds(mailId);
			mail.deleteMail(playerId);
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
		List<Long> dels = new ArrayList<>();
		Collection<IMail> mails = mailManager.getMails(playerId);
		for (IMail mail : mails) {
			if (mail.canDel(playerId)) {
				mail.deleteMail(playerId);
				dels.add(mail.getId());
			}
		}
		resp.addAllMailIds(dels);
		//log
		return ErrorCode.SUCCESS;
	}
	
	/////////////接口方法////////////////////////
	
	@Override
	public ErrorCode sendMail(BackstageMail backstageMail) {
		IMailServiceContainer mailContainer = mailManager.getMailContainer(backstageMail.getMailType());
		if (mailContainer == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		ResultCodeData<Long> resultCodeData = mailContainer.sendMail(backstageMail);
		return resultCodeData.getErrorCode();
	}

	@Override
	public ErrorCode sendMail(int mailType, long playerId, int configID, ResourceGroup rewards, Object... args) {
		IMailServiceContainer mailContainer = mailManager.getMailContainer(mailType);
		if (mailContainer == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		ResultCodeData<Long> resultCodeData = mailContainer.sendMail(playerId, configID, rewards, args);
		return resultCodeData.getErrorCode();
	}

	@Override
	public ErrorCode sendMail(int mailType, long playerId, String title, String content, int expiredDays,
			ResourceGroup rewards) {
		IMailServiceContainer mailContainer = mailManager.getMailContainer(mailType);
		if (mailContainer == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		ResultCodeData<Long> resultCodeData = mailContainer.sendMail(playerId, title, content, expiredDays, rewards);
		return resultCodeData.getErrorCode();
	}

	@Override
	public ErrorCode deleteMail(int mailType, long mailId, long playerId) {
		IMailServiceContainer mailContainer = mailManager.getMailContainer(mailType);
		if (mailContainer == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		return mailContainer.deleteMail(mailId, playerId);
	}

	@Override
	public ErrorCode updateMail(int mailType, long mailId, long playerId, String title, String content, int expiredDays,
			ResourceGroup rewards) {
		IMailServiceContainer mailContainer = mailManager.getMailContainer(mailType);
		if (mailContainer == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		return mailContainer.updateMail(mailId, playerId, title, content, expiredDays, rewards);
	}

	@Override
	public IMail getMail(int mailType, long mailId, long playerId) {
		IMailServiceContainer mailContainer = mailManager.getMailContainer(mailType);
		if (mailContainer == null) {
			return null;
		}
		return mailContainer.getMail(mailId, playerId);
	}

	@Override
	public Collection<? extends IMail> getMails(int mailType, long playerId) {
		IMailServiceContainer mailContainer = mailManager.getMailContainer(mailType);
		if (mailContainer == null) {
			return Collections.emptyList();
		}
		return mailContainer.getMails(playerId);
	}

}