package com.cat.server.game.module.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.core.server.AbstractController;
import com.cat.server.core.server.IModuleService;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mail.proto.*;
import com.cat.server.game.data.proto.PBMail.*;

/**
 * Mail控制器
 */
@Controller
public class MailController extends AbstractController{
	
	private static final Logger log = LoggerFactory.getLogger(MailController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private MailService mailService;
	
	
	/*
	*获取邮件附件
	*/
	@Cmd(value = PBProtocol.ReqPlayerMailReward_VALUE)
	public void reqMailReward(ISession session, ReqMailReward req) {
		long playerId = session.getUserData();
		RespMailRewardBuilder ack = RespMailRewardBuilder.newInstance();
		ErrorCode code = mailService.reqMailReward(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求读取邮件
	*/
	@Cmd(value = PBProtocol.ReqPlayerMailRead_VALUE)
	public void reqMailRead(ISession session, ReqMailRead req) {
		long playerId = session.getUserData();
		RespMailReadBuilder ack = RespMailReadBuilder.newInstance();
		ErrorCode code = mailService.reqMailRead(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求邮件列表
	*/
	@Cmd(value = PBProtocol.ReqPlayerMailList_VALUE)
	public void reqMailList(ISession session, ReqMailList req) {
		long playerId = session.getUserData();
		mailService.reqMailList(playerId);
	}
	
	/*
	*请求删除邮件
	*/
	@Cmd(value = PBProtocol.ReqPlayerMailDelete_VALUE)
	public void reqMailDelete(ISession session, ReqMailDelete req) {
		long playerId = session.getUserData();
		RespMailDeleteBuilder ack = RespMailDeleteBuilder.newInstance();
		ErrorCode code = mailService.reqMailDelete(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}

	@Override
	public IModuleService getService() {
		return mailService;
	}
	

}
