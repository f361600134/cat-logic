package com.cat.server.game.module.gm.type.gen;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.net.network.base.GameSession;
import com.cat.server.game.data.proto.PBPlayer;
import com.cat.server.game.module.gm.annotation.Command;
import com.cat.server.game.module.gm.type.AbstractModuleCommand;
import com.cat.server.game.module.playermail.controller.PlayerMailController;
import com.cat.server.game.module.playermail.service.PlayerMailService;

/**
 * @mail
 * @author Jeremy
 */
@Component
@Command("@mail")
public class CommandMail extends AbstractModuleCommand{
	
	@Autowired
	private PlayerMailService mailService;
	
	@Autowired
	private PlayerMailController controller;
	
	/**
	 * @mail sendMail,1
	 */
	public void sendMail(GameSession session, String data[]) {
		long playerId = session.getPlayerId();
		int configId = Integer.valueOf(data[1]);
		mailService.sendMail(playerId, configId, new HashMap<>(), "");
	}
	
	/**
	 * @mail reqReadEmail,1
	 */
	public void reqReadEmail(GameSession session, String data[]) {
		PBPlayer.ReqReadEmail.Builder req = PBPlayer.ReqReadEmail.newBuilder();
		req.setId(Long.parseLong(data[1]));
		controller.reqReadEmail(session, req.build());
	}
	
	/**
	 * 领取邮件奖励
	 * @mail reqReceiveEmail,1
	 */
	public void reqReceiveEmail(GameSession session, String data[]) {
		PBPlayer.ReqReceiveEmail.Builder req = PBPlayer.ReqReceiveEmail.newBuilder();
		req.setId(Long.parseLong(data[1]));
		controller.reqReceiveEmail(session, req.build());
	}
	
	/**
	 * 删除邮件
	 * @mail reqDeleteEmail,1
	 */
	public void reqDeleteEmail(GameSession session, String data[]) {
		PBPlayer.ReqDeleteEmail.Builder req = PBPlayer.ReqDeleteEmail.newBuilder();
		req.setId(Long.parseLong(data[1]));
		controller.reqDeleteEmail(session, req.build());
	}

}
