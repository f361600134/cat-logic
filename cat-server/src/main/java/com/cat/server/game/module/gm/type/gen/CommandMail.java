package com.cat.server.game.module.gm.type.gen;

import com.cat.server.game.module.gm.annotation.Command;
import com.cat.server.game.module.gm.type.AbstractModuleCommand;

/**
 * @mail
 * @author Jeremy
 */
@Command("@mail")
public class CommandMail extends AbstractModuleCommand{
	
//	@Autowired
//	private PlayerMailService mailService;
//	
//	@Autowired
//	private PlayerMailController controller;
//	
//	/**
//	 * @mail sendMail,1
//	 */
//	public void sendMail(ISession session, String data[]) {
//		long playerId = session.getUserData();
//		int configId = Integer.valueOf(data[1]);
//		mailService.sendMail(playerId, configId, new HashMap<>(), "");
//	}
//	
//	/**
//	 * @mail reqMailRead,1
//	 */
//	public void reqMailRead(ISession session, String data[]) {
//		PBPlayerMail.ReqMailRead.Builder req = PBPlayerMail.ReqMailRead.newBuilder();
//		req.setMailId(Integer.parseInt(data[1]));
//		controller.reqMailRead(session, req.build());
//	}
//	
//	/**
//	 * 领取邮件奖励
//	 * @mail reqMailReward,1
//	 */
//	public void reqMailReward(ISession session, String data[]) {
//		PBPlayerMail.ReqMailReward.Builder req = PBPlayerMail.ReqMailReward.newBuilder();
//		req.setMailId(Long.parseLong(data[1]));
//		controller.reqMailReward(session, req.build());
//	}
//	
//	/**
//	 * 删除邮件
//	 * @mail reqMailDelete,1
//	 */
//	public void reqMailDelete(ISession session, String data[]) {
//		PBPlayerMail.ReqMailDelete.Builder req = PBPlayerMail.ReqMailDelete.newBuilder();
//		req.setMailId(Long.parseLong(data[1]));
//		controller.reqMailDelete(session, req.build());
//	}

}
