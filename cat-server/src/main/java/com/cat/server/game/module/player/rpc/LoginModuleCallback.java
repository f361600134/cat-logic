package com.cat.server.game.module.player.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.rpc.IResponseCallback;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.data.proto.PBLogin.AckLogin;

/**
 * 登录回调, 如果账号服与游戏服长连接的话, 
 * @author Jeremy
 */
@Rpc(value = PBDefine.PBProtocol.AckPlayerLogin_VALUE)
public class LoginModuleCallback implements IResponseCallback<AckLogin>{
	
	private static Logger logger = LoggerFactory.getLogger(LoginModuleCallback.class);

	@Override
	public void receiveResponse(AckLogin response) {
		logger.info("RPC响应返回=========> response.code:{}, status:{}", response.getCode(), response.getStatus());
	}

	@Override
	public void handleException(Exception ex) {
		logger.info("RPC响应返回异常========> response error:{}", ex);
	}

}


