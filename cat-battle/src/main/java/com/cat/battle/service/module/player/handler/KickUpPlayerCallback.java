package com.cat.battle.service.module.player.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.api.ProtocolId;
import com.cat.api.response.RespKickUpPlayer;
import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.rpc.IResponseCallback;

/**
 * 登录回调, 如果账号服与游戏服长连接的话, 
 * @author Jeremy
 */
@Rpc(value = ProtocolId.RespKickUpPlayer, isAuth = false)
public class KickUpPlayerCallback implements IResponseCallback<RespKickUpPlayer>{
	
	private static Logger logger = LoggerFactory.getLogger(RespKickUpPlayer.class);

	@Override
	public void receiveResponse(RespKickUpPlayer response) {
		logger.info("RPC踢人回调, response.code:{}", response.getCode());
	}

	@Override
	public void handleException(Exception ex) {
		logger.info("RPC响应踢人返回异常========> response error:{}", ex);
	}

}


