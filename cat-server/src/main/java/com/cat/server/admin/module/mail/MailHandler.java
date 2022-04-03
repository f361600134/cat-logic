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
import com.cat.server.game.module.mail.IMailService;
import com.cat.server.game.module.resource.domain.ResourceGroup;

/**
 * 统一使用玩家线程去处理
 * @author Jeremy
 */
@Controller
@RequestMapping("/mail")
public class MailHandler {
	
	@Autowired private IMailService mailService;
	
	@Autowired private IMailMapper mailMapper;
	
	/**
	 * 发送个人邮件
	 * http://localhost:8001/mail/sendMail?mailType=2&title=test&context=testcontext&expireDays=7
	 */
	@RequestMapping("/sendMail")
	public IResult sendMail(BackstageMail mail) {
		return SystemResult.build(mailService.sendMail(mail));
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
		final String content = mail.getContent();
		final int expireDays = mail.getExpireDays();
		final ResourceGroup reward = new ResourceGroup(mail.getReward());
		ErrorCode errorCode = mailService.updateMail(mailType, mailId, playerId, title, content, expireDays, reward);
		return SystemResult.build(errorCode);
	}
	
	/**
	 * 查看玩家邮件列表
	 * http://localhost:8001/mail/selectMails?mailType=2&playerId=0
	 */
	@RequestMapping("/selectMails")
	public IResult selectPersonMails(BackstageMail mail) {
		final int mailType = mail.getMailType();
		final long playerId = mail.getPlayerId();
		
		//游戏邮件转后台邮件
//		List<BackstageMail> rets = new ArrayList<>();
//		Collection<? extends IMail> gameMails = mailService.getMails(mailType, playerId);
//		for (IMail gameMail : gameMails) {
//			rets.add(BackstageMail.create(gameMail));
//		}
		Collection<BackstageMail> rets = mailMapper.toDto(mailService.getMails(mailType, playerId));
		return SystemResult.build(SystemCodeEnum.SUCCESS, rets);
	}

}
