package com.cat.server.game.rpc.callback;

import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.rpc.event.RpcIdentityAuthenticateSuccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.api.ProtocolId;
import com.cat.api.module.battle.response.RespIdentityAuthenticate;
import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.rpc.IResponseCallback;

/**
 * 登录回调, 如果账号服与游戏服长连接的话, 
 * @author Jeremy
 */
@Rpc(value = ProtocolId.RespIdentityAuthenticate, listen = Rpc.RESPONSE)
public class ReqIdentityAuthenticateCallback implements IResponseCallback<RespIdentityAuthenticate>{
	
	private static Logger logger = LoggerFactory.getLogger(ReqIdentityAuthenticateCallback.class);

	@Override
	public void receiveResponse(RespIdentityAuthenticate response) {
		logger.info("RPC响应返回=========> response.code:{}", response.getCode());
		if (response.getCode() != 0){
			logger.info("RPC身份验证不通过, errorcode:{}", response.getCode());
			return;
		}
		//处理rpc连接成功逻辑, 发送rpc验证成功事件,使有rpc逻辑的模块去处理响应逻辑
		GameEventBus.getInstance().post(RpcIdentityAuthenticateSuccessEvent.create(response.getNodeType()));
	}

	@Override
	public void handleException(Exception ex) {
		logger.info("RPC响应返回异常========> response error:{}", ex);
	}

}


