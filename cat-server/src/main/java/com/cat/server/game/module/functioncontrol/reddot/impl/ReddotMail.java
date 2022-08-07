package com.cat.server.game.module.functioncontrol.reddot.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cat.server.game.module.functioncontrol.define.ReddotConditionEnum;
import com.cat.server.game.module.functioncontrol.reddot.IFunctionReddot;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.IMailService;
import com.cat.server.game.module.mail.assist.MailState;
import com.cat.server.game.module.mail.assist.MailType;

/**
 * 红点条件-新邮件/未读邮件
 * @author Jeremy
 */
@Component
public class ReddotMail implements IFunctionReddot {
	
	@Autowired private IMailService mailService;

	@Override
	public int getCondition() {
		return ReddotConditionEnum.MAIL.getConditionId();
	}

	@Override
	public int checkReddot(long playerId) {
		List<IMail> mails = new ArrayList<>();
		mails.addAll(mailService.getMails(MailType.PLAYER_MAIL.getMailType(), playerId));
		mails.addAll(mailService.getMails(MailType.GROUP_MAIL.getMailType(), playerId));
		if (mails.isEmpty()) {
			return 0;
		}
		int count = 0;
		for (IMail mail : mails) {
			if (mail.getState(playerId) == MailState.NONE.getState()) {
				count ++;
			}
		}
		return count;
	}

}
