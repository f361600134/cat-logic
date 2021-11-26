package com.cat.server.game.module.mail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.data.proto.PBMail.PBMailInfo;
import com.cat.server.game.data.proto.PBMail.ReqMailDelete;
import com.cat.server.game.data.proto.PBMail.ReqMailRead;
import com.cat.server.game.data.proto.PBMail.ReqMailReward;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mail.assist.MailConstant;
import com.cat.server.game.module.mail.assist.MailState;
import com.cat.server.game.module.mail.domain.Mail;
import com.cat.server.game.module.mail.proto.RespMailDeleteBuilder;
import com.cat.server.game.module.mail.proto.RespMailListBuilder;
import com.cat.server.game.module.mail.proto.RespMailReadBuilder;
import com.cat.server.game.module.mail.proto.RespMailRewardBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.google.common.collect.Lists;


/**
 * Mail控制器
 */
@Service
public class MailService implements IMailService {
	
	private static final Logger log = LoggerFactory.getLogger(MailService.class);
	
	@Autowired 
	private IPlayerService playerService;
	
//	@Autowired private MailManager mailManager;
	
	@Autowired private IResourceGroupService resourceGroupService;
	
	@Autowired private List<IMailServiceContainer> mailServiceContainer;
	
	/**
	 * 登陆,  下发所有邮件
	 * 邮件模块不同于其他模块, 邮件的触发不仅仅由玩家触发,所以登录后就得发送所有邮件
	 */
	public void onLogin(long playerId) {
//		MailDomain domain = mailManager.getDomain(playerId);
//		Collection<Mail> beans = domain.getBeans();
//		log.info("邮件内容:{}",beans);
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
//		mailManager.remove(playerId);
	}
	
	/**
	 * 更新信息
	 */
	public void responseMailInfos(long playerId) {
		RespMailListBuilder resp = RespMailListBuilder.newInstance();
		for (IMailServiceContainer mailServiceContainer : mailServiceContainer) {
			resp.addAllMails(mailServiceContainer.toProto(playerId));
		}
		playerService.sendMessage(playerId, resp);
	}
	
	/**
	 * 更新信息, 通知客户端多封新邮件
	 */
	public void responseMailInfo(long playerId, List<Long> mailIds) {
		RespMailListBuilder resp = RespMailListBuilder.newInstance();
		for (IMailServiceContainer mailServiceContainer : mailServiceContainer) {
			resp.addAllMails(mailServiceContainer.toProto(playerId, mailIds));
		}
		playerService.sendMessage(playerId, resp);
	}
	
	public IMailServiceContainer getServiceContainer(int mailType) {
		return mailServiceContainer.stream().filter(m->m.mailType() == mailType).findFirst().get();
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
		//final long mailType = req.getMailType();
		final int mailType = 0;
		IMailServiceContainer container= getServiceContainer(mailType);
		return container.markeAsRead(mailId, playerId);
	}

	
	/**
	* 获取邮件附件
	* @param long playerId 玩家id
	* @param ReqMailReward req 请求消息
	* @param RespMailRewardResp ack 响应消息
	*/
	public ErrorCode reqMailReward(long playerId, ReqMailReward req, RespMailRewardBuilder ack){
		final long mailId = req.getMailId();
		//final long mailType = req.getMailType();
		final int mailType = 0;
		if (mailId < 0) {
			return receiveAll(playerId, mailId, ack);
		}else {
			return receiveOne(playerId, mailType, mailId, ack);
		}
	}

	/**
	 * 领取一封邮件
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	private ErrorCode receiveOne(long playerId, int mailType, long mailId, RespMailRewardBuilder ack) {
		IMailServiceContainer container = getServiceContainer(mailType);
		IMail mail = container.getMail(mailId, playerId);
		if (container.getState(mailId, playerId) == MailState.REWARD.getState()) {
			return ErrorCode.MAIL_ALREADY_REWARD;
		}else if (mail.isExpired()) {
			return ErrorCode.MAIL_ALREADY_REWARD;
		}
		ErrorCode errorCode = container.markAsReward(mailId, playerId);
		if (!errorCode.isSuccess()) {
			return errorCode;
		}
		//领取附件
		resourceGroupService.reward(playerId, mail.getRewardMap(), NatureEnum.MailReward);
		//更新状态
		this.responseMailInfo(playerId, Lists.newArrayList(mailId));
		ack.addAllRewards(ResourceHelper.toPairProto(mail.getRewardMap()));
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 一键领取
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	private ErrorCode receiveAll(long playerId, long mailId, RespMailRewardBuilder ack) {
//		MailDomain domain = mailManager.getDomain(playerId);
//		if (domain == null) {
//			return ErrorCode.MAIL_BOX_NOT_FOUND;
//		}
		Map<Integer, Integer> rewardMap = new HashMap<>();
		for (IMailServiceContainer mailServiceContainer : mailServiceContainer) {
			
		}
//		List<Long> mailIds = new ArrayList<>();
//		for (Mail mail : domain.getBeanMap().values()) {
//			if (mail == null || mail.isExpired() || mail.isRewarded())
//				continue;
//
//			//merge reward
//			ResourceHelper.mergeToMap(mail.getRewardMap(), rewardMap);
//			mailIds.add(mail.getId());
//			
//			mail.setState(MailConstant.REWARD);
//			mail.update();
//			
//		}
		//没有可领取奖励
		if (rewardMap.isEmpty()) {
			return ErrorCode.MAIL_ALREADY_NO_REWARD;
		}
		//奖励入背包
		resourceGroupService.reward(playerId, rewardMap, NatureEnum.MailReward);
		//更新状态
		this.responseMailInfo(domain, mailIds);
		//更新奖励
		ack.addAllRewards(ResourceHelper.toPairProto(rewardMap));
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
		MailDomain domain = mailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		Mail mail = domain.getBean(mailId);
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
		MailDomain domain = mailManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.MAIL_BOX_NOT_FOUND;
		}
		List<Long> dels = new ArrayList<>();
		for (Mail mail : domain.getBeanMap().values()) {
			if (mail.canDel()) {
				dels.add(mail.getId());
				mail.delete();
			}
		}
		resp.addAllMailIds(dels);
		//log
		return ErrorCode.SUCCESS;
	}
	
	
	
	/////////////接口方法////////////////////////
}