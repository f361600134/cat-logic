package com.cat.server.game.module.player.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.api.ProtocolId;
import com.cat.api.response.RespIdentityAuthenticate;
import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.rpc.IResponseCallback;

/**
 * 登录回调, 如果账号服与游戏服长连接的话, 
 * @author Jeremy
 */
@Rpc(value = ProtocolId.RespIdentityAuthenticate)
public class ReqIdentityAuthenticateCallback implements IResponseCallback<RespIdentityAuthenticate>{
	
	private static Logger logger = LoggerFactory.getLogger(ReqIdentityAuthenticateCallback.class);

	@Override
	public void receiveResponse(RespIdentityAuthenticate response) {
		logger.info("RPC响应返回=========> response.code:{}", response.getCode());
	}

	@Override
	public void handleException(Exception ex) {
		logger.info("RPC响应返回异常========> response error:{}", ex);
	}

}


