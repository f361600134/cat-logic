package com.cat.server.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.net.network.client.RpcClientStarter;
import com.cat.server.common.ServerConstant;
import com.cat.server.core.lifecycle.Lifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.module.player.proto.ReqLogin;
import com.cat.server.game.module.player.rpc.LoginModuleCallback;
import com.rpc.core.client.RequesterService;

/**
 * 这玩意儿其实是个测试类
 * @author Jeremy
 */
@Component
public class RpcRunner implements Lifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(RpcRunner.class);
	
//	@Autowired 
//	private RequesterService requesterService;
	
	
	public RpcRunner() {
	}
	
	
	public void run() throws Exception {
		try {
//			/**
//			 * 随机获取一个分组下的节点
//			 */
//			RpcClientStarter client = requesterService.getClient(ServerConstant.NODE_TYPE_LOGIN);
//			log.info("节点类型: {}, 客户端:{}", ServerConstant.NODE_TYPE_LOGIN, client);
//			
//			client.ask(ReqLogin.create("aaa"), 100L, new LoginModuleCallback());
		} catch (Exception e) {
			log.error("服务器初始化过程出现异常, 启动失败", e);
		}
	}
	
	@Override
	public void start() throws Throwable {
		this.run();
	}
	
	
	public int priority() {
		return Priority.LOWEST.getPriority();
	}
}
