package com.cat.server.game.module.playermail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.GameSession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.playermail.proto.AckDeleteEmailResp;
import com.cat.server.game.module.playermail.proto.AckReadEmailResp;
import com.cat.server.game.module.playermail.proto.AckReceiveEmailResp;
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
	@Cmd(PBProtocol.ReqEmailList_VALUE)
	public void reqEmailList(GameSession session, PBPlayer.ReqEmailList req) {
		playerMailService.responsePlayerMailInfo(session.getPlayerId());
	}

	/**
	 * @param session
	 * @return
	 */
	/**阅读邮件*/
	@Cmd(PBProtocol.ReqReadEmail_VALUE)
	public void reqReadEmail(GameSession session, PBPlayer.ReqReadEmail req) {
		final long playerId = session.getPlayerId();
		AckReadEmailResp resp = AckReadEmailResp.newInstance();
		ErrorCode errorCode = playerMailService.read(playerId, req.getId(), resp);
		resp.setCode(errorCode.getCode());
		playerService.sendMessage(playerId, resp);
	}
	
	/** 
	 * @param session
	 * @return
	 */
	/**领取邮件*/
	@Cmd(PBProtocol.ReqReceiveEmail_VALUE)
	public void reqReceiveEmail(GameSession session, PBPlayer.ReqReceiveEmail req) {
		final long playerId = session.getPlayerId();
		AckReceiveEmailResp resp = AckReceiveEmailResp.newInstance();
		ErrorCode errorCode = playerMailService.receive(playerId, req.getId());
		resp.setCode(errorCode.getCode());
		playerService.sendMessage(playerId, resp);
	}

	
	/**
	 * @param session
	 * @return
	 */
	/**删除邮件*/
	@Cmd(PBProtocol.ReqDeleteEmail_VALUE)
	public void reqDeleteEmail(GameSession session, PBPlayer.ReqDeleteEmail req) {
		final long playerId = session.getPlayerId();
		AckDeleteEmailResp resp = AckDeleteEmailResp.newInstance();
		ErrorCode errorCode = playerMailService.delete(playerId, req.getId(), resp);
		resp.setCode(errorCode.getCode());
		playerService.sendMessage(playerId, resp);
	}
	

}
