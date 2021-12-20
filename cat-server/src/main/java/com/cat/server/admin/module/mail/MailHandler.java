package com.cat.server.admin.module.mail;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.http.annatation.RequestMapping;
import com.cat.server.admin.helper.result.IResult;
import com.cat.server.admin.helper.result.SystemCodeEnum;
import com.cat.server.admin.helper.result.SystemResult;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.IMailService;

/**
 * 统一使用玩家线程去处理
 * @author Jeremy
 */
@Controller
@RequestMapping("/mail")
public class MailHandler {
	
	@Autowired private IMailService mailService;
	
	/**
	 * 发送个人邮件
	 * http://localhost:8001/mail/sendMail?mailType=2&title=test&context=testcontext&expireDays=7
	 */
	@RequestMapping("/sendMail")
	public IResult sendMail(BackstageMail mail) {
		final int mailType = mail.getMailType();
		final long playerId = mail.getPlayerId();
		final String title = mail.getTitle();
		final String context = mail.getContext();
		final int expireDays = mail.getExpireDays();
		final Map<Integer, Integer> reward= mail.getReward();
		
		ErrorCode errorCode = mailService.sendMail(mailType, playerId, title, context, expireDays, reward);
		return SystemResult.build(errorCode);
	}
	
	/**
	 * 删除个人邮件/删除群邮件
	 * http://localhost:8001/mail/deleteMail?mailType=2&mailId=786344627299946496
	 */
	@RequestMapping("/deleteMail")
	public IResult deletePersonMail(BackstageMail mail) {
		final int mailType = mail.getMailType();
		final long playerId = mail.getPlayerId();
		final long mailId = mail.getMailId();
		ErrorCode errorCode = mailService.deleteMail(mailType, mailId, playerId);
		return SystemResult.build(errorCode);
	}
	
	/**
	 * 修改个人邮件
	 * http://localhost:8001/mail/updateMail?mailType=2&mailId=786344627299946496&title=testtesttest&context=testtesttest&expireDays=10
	 */
	@RequestMapping("/updateMail")
	public IResult updatePersonMail(BackstageMail mail) {
		final int mailType = mail.getMailType();
		final long playerId = mail.getPlayerId();
		final long mailId = mail.getMailId();
		final String title = mail.getTitle();
		final String context = mail.getContext();
		final int expireDays = mail.getExpireDays();
		final Map<Integer, Integer> reward= mail.getReward();
		ErrorCode errorCode = mailService.updateMail(mailType, mailId, playerId, title, context, expireDays, reward);
		return SystemResult.build(errorCode);
	}
	
	/**
	 * 查看玩家邮件列表
	 */
	@RequestMapping("/selectMails")
	public IResult selectPersonMails(BackstageMail mail) {
		final int mailType = mail.getMailType();
		final long playerId = mail.getPlayerId();
		Collection<? extends IMail> rets = mailService.getMails(mailType, playerId);
		return SystemResult.build(SystemCodeEnum.SUCCESS, rets);
	}

}
