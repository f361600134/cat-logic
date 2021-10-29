package com.cat.server.game.module.console.type.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.game.module.console.annotation.Console;
import com.cat.server.game.module.console.type.IConsole;
import com.rpc.common.RpcConfig;
import com.rpc.core.client.RequesterManager;

/**
 * 控制台命令,更新指定排行榜
 * @author Jeremy
 */
@Console("rank")
public class ConsoleRank implements IConsole {

	private static final Logger log = LoggerFactory.getLogger(ConsoleRank.class);
	
	@Autowired private RpcConfig rpcConfig;
	
	@Autowired private RequesterManager requesterManager;

	@Override
	public void process(String content) {
		try {
			//rpc请求
//			RpcClientStarter client = requesterManager.getClient(ServerConstant.NODE_TYPE_RANK);
//			if(client == null) {
//				log.info("没有找到合适的节点, 节点类型{}", ServerConstant.NODE_TYPE_RANK);
//				return true;
//			}
//			ReqIdentityAuthenticate req = ReqIdentityAuthenticate.create(rpcConfig.getServerId(), rpcConfig.getNodeType());
//			log.info("目标节点类型: {}, 发送验证参数:[{}|{}], 客户端:{}", ServerConstant.NODE_TYPE_RANK, rpcConfig.getServerId(), rpcConfig.getNodeType(), client);
//			client.ask(req, 300L, new ReqIdentityAuthenticateCallback());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
