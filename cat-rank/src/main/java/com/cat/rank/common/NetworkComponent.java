package com.cat.rank.common;

import com.cat.api.core.task.impl.CommonTaskExecutor;
import com.cat.net.network.controller.DefaultRpcDispatcher;
import com.cat.net.network.controller.IRpcController;
import com.rpc.core.server.RpcNetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 网络组件
 * 可以根据需要注册
 * @author Jeremy
 */
@Configuration
public class NetworkComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(NetworkComponent.class);
	
	/**
	 * 注册游戏服分发处理器
	 * @return RPC网络服务
	 */
	@Bean
	public RpcNetService netService() {
		logger.info("注册[RpcNetService]服务");
		return new RpcNetService();
	}
	
	/**
	 * RPC客户端服务
	 * @return 默认RPC转发器
	 */
	@Bean
	public DefaultRpcDispatcher rpcController(List<IRpcController> callbacks) {
		logger.info("注册[DefaultRpcDispatcher]服务, size:{}", callbacks.size());
		DefaultRpcDispatcher controller = new DefaultRpcDispatcher();
		try {
			controller.initialize(callbacks);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册[DefaultRpcDispatcher]服务失败, 异常:", e);
		}
		return controller;
	}
	
	/**
	 * 注册默认的公共线程池
	 * @return
	 */
	@Bean
	public CommonTaskExecutor commonExecutor() {
		logger.info("注册[CommonTaskExecutor]服务");
		return new CommonTaskExecutor();
	}
}
