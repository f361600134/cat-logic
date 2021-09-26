package com.cat.server.game.module.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IController;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerCreateRole;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerHeart;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerLogin;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerRandName;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerReLogin;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.proto.AckPlayerCreateRoleResp;
import com.cat.server.game.module.player.proto.AckPlayerHeartResp;
import com.cat.server.game.module.player.proto.AckPlayerLoginResp;
import com.cat.server.game.module.player.proto.AckPlayerRandNameResp;
import com.cat.server.game.module.player.proto.AckPlayerReLoginResp;

/**
 * Player控制器
 */
@Controller
public class PlayerController implements IController{
	
	private static final Logger log = LoggerFactory.getLogger(PlayerController.class);
	
	@Autowired
	private PlayerService playerService;
	
	
	/*
	*请求获取随机名
	*/
	@Cmd(value = PBProtocol.ReqPlayerRandName_VALUE)
	public void ReqPlayerRandName(ISession session, ReqPlayerRandName req) {
		long playerId = session.getUserData();
		AckPlayerRandNameResp ack = AckPlayerRandNameResp.newInstance();
		playerService.reqPlayerRandName(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求断线重连
	*/
	@Cmd(value = PBProtocol.ReqPlayerReLogin_VALUE)
	public void ReqPlayerReLogin(ISession session, ReqPlayerReLogin req) {
		long playerId = session.getUserData();
		AckPlayerReLoginResp ack = AckPlayerReLoginResp.newInstance();
		ErrorCode code = playerService.reqPlayerReLogin(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求心跳
	*/
	@Cmd(value = PBProtocol.ReqPlayerHeart_VALUE)
	public void ReqPlayerHeart(ISession session, ReqPlayerHeart req) {
		long playerId = session.getUserData();
		AckPlayerHeartResp ack = AckPlayerHeartResp.newInstance();
		playerService.reqPlayerHeart(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求创建角色
	*/
	@Cmd(value = PBProtocol.ReqPlayerCreateRole_VALUE)
	public void ReqPlayerCreateRole(ISession session, ReqPlayerCreateRole req) {
		long playerId = session.getUserData();
		AckPlayerCreateRoleResp ack = AckPlayerCreateRoleResp.newInstance();
		ErrorCode code = playerService.reqPlayerCreateRole(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求连接游戏服
	*/
	@Cmd(value = PBProtocol.ReqPlayerLogin_VALUE, mustLogin = false)
	public void ReqPlayerLogin(ISession session, ReqPlayerLogin req) {
		AckPlayerLoginResp ack = AckPlayerLoginResp.newInstance();
		ErrorCode code = playerService.reqPlayerLogin(session, req, ack);
		ack.setCode(code.getCode());
		
		Long playerId = session.getUserData();
		if (playerId != null) {
			playerService.sendMessage(playerId.longValue(), ack);
		}
	}

}
