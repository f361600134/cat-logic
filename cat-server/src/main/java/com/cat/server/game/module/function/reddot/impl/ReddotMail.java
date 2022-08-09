package com.cat.server.game.module.function.reddot.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.game.module.function.define.ReddotTypeEnum;
import com.cat.server.game.module.function.reddot.IFunctionReddot;
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
	public int getReddotId() {
		return ReddotTypeEnum.MAIL.getReddotId();
	}

	@Override
	public List<Integer> checkReddot(long playerId) {
		List<IMail> mails = new ArrayList<>();
		mails.addAll(mailService.getMails(MailType.PLAYER_MAIL.getMailType(), playerId));
		mails.addAll(mailService.getMails(MailType.GROUP_MAIL.getMailType(), playerId));
		if (mails.isEmpty()) {
			return Collections.emptyList();
		}
		int count = 0;
		for (IMail mail : mails) {
			if (mail.getState(playerId) == MailState.NONE.getState()) {
				count ++;
			}
		}
		return Collections.singletonList(count);
	}

}
