//package com.cat.server.game.module.playermail;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import com.cat.net.network.annotation.Cmd;
//import com.cat.net.network.base.ISession;
//import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.cat.server.game.module.player.IPlayerService;
//import com.cat.server.game.helper.result.ErrorCode;
//import com.cat.server.game.module.playermail.proto.*;
////import com.cat.server.game.data.proto.*;
//import com.cat.server.game.data.proto.PBPlayerMail.*;
//
///**
// * PlayerMail控制器
// */
//@Controller
//public class PlayerMailController {
//	
//	private static final Logger log = LoggerFactory.getLogger(PlayerMailController.class);
//  
//	@Autowired private IPlayerService playerService;
//	
//	@Autowired
//	private PlayerMailService playerMailService;
//	
//	
//	/*
//	*获取邮件附件
//	*/
//	@Cmd(value = PBProtocol.ReqMailReward_VALUE)
//	public void reqMailReward(ISession session, ReqMailReward req) {
//		long playerId = session.getUserData();
//		RespMailRewardBuilder ack = RespMailRewardBuilder.newInstance();
//		ErrorCode code = playerMailService.reqMailReward(playerId, req, ack);
//		ack.setCode(code.getCode());
//		playerService.sendMessage(playerId, ack);
//	}
//	
//	/*
//	*请求读取邮件
//	*/
//	@Cmd(value = PBProtocol.ReqMailRead_VALUE)
//	public void reqMailRead(ISession session, ReqMailRead req) {
//		long playerId = session.getUserData();
//		RespMailReadBuilder ack = RespMailReadBuilder.newInstance();
//		ErrorCode code = playerMailService.reqMailRead(playerId, req, ack);
//		ack.setCode(code.getCode());
//		playerService.sendMessage(playerId, ack);
//	}
//	
//	/*
//	*请求邮件列表
//	*/
//	@Cmd(value = PBProtocol.ReqMailList_VALUE)
//	public void reqMailList(ISession session, ReqMailList req) {
//		long playerId = session.getUserData();
//		//FIXME 这里怎么才可以通用呢? 
////		RespMailListBuilder ack = RespMailListBuilder.newInstance();
////		playerMailService.reqMailList(playerId, req, ack);
////		playerService.sendMessage(playerId, ack);
//		playerMailService.reqMailList(playerId);
//	}
//	
//	/*
//	*请求删除邮件
//	*/
//	@Cmd(value = PBProtocol.ReqMailDelete_VALUE)
//	public void reqMailDelete(ISession session, ReqMailDelete req) {
//		long playerId = session.getUserData();
//		RespMailDeleteBuilder ack = RespMailDeleteBuilder.newInstance();
//		ErrorCode code = playerMailService.reqMailDelete(playerId, req, ack);
//		ack.setCode(code.getCode());
//		playerService.sendMessage(playerId, ack);
//	}
//	
//
//}
