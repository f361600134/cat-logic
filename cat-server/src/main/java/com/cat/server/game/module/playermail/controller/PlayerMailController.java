package com.cat.server.game.module.playermail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBMail;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.playermail.proto.AckMailDeleteResp;
import com.cat.server.game.module.playermail.proto.AckMailReadResp;
import com.cat.server.game.module.playermail.proto.AckMailRewardResp;
import com.cat.server.game.module.playermail.service.PlayerMailService;

/**
 * PlayerMail控制器
 */
@Controller
public class PlayerMailController {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMailController.class);
	
	@Autowired
	private PlayerMailService playerMailService;
	
	@Autowired
	private IPlayerService playerService;
	
	/**
	 * @param session
	 * @return
	 */
	/**获取邮件列表*/
	@Cmd(PBProtocol.ReqMailList_VALUE)
	public void reqEmailList(ISession session, PBMail.ReqMailList req) {
		playerMailService.responsePlayerMailInfo(session.getUserData());
	}

	/**
	 * @param session
	 * @return
	 */
	/**阅读邮件*/
	@Cmd(PBProtocol.ReqMailRead_VALUE)
	public void reqMailRead(ISession session, PBMail.ReqMailRead req) {
		final long playerId = session.getUserData();
		AckMailReadResp resp = AckMailReadResp.newInstance();
		ErrorCode errorCode = playerMailService.read(playerId, req.getMailId(), resp);
		resp.setCode(errorCode.getCode());
		playerService.sendMessage(playerId, resp);
	}
	
	/** 
	 * @param session
	 * @return
	 */
	/**领取邮件*/
	@Cmd(PBProtocol.ReqMailReward_VALUE)
	public void reqMailReward(ISession session, PBMail.ReqMailReward req) {
		final long playerId = session.getUserData();
		AckMailRewardResp resp = AckMailRewardResp.newInstance();
		ErrorCode errorCode = playerMailService.receive(playerId, req.getMailId());
		resp.setCode(errorCode.getCode());
		playerService.sendMessage(playerId, resp);
	}

	
	/**
	 * @param session
	 * @return
	 */
	/**删除邮件*/
	@Cmd(PBProtocol.ReqMailDelete_VALUE)
	public void reqEmailDelete(ISession session, PBMail.ReqMailDelete req) {
		final long playerId = session.getUserData();
		AckMailDeleteResp resp = AckMailDeleteResp.newInstance();
		ErrorCode errorCode = playerMailService.delete(playerId, req.getMailId(), resp);
		resp.setCode(errorCode.getCode());
		playerService.sendMessage(playerId, resp);
	}
	

}
