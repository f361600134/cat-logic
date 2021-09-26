package com.cat.battle.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cat.net.network.controller.IRpcController;
import com.cat.net.network.controller.RpcDispatcher;
import com.rpc.core.server.RpcNetService;

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
	 * @return
	 */
	@Bean
	public RpcNetService netService() {
		logger.info("注册[RpcNetService]服务");
		return new RpcNetService();
	}
	
//	/**
//	 * RPC服务端服务
//	 * @return
//	 */
//	@Bean
//	public DefaultRemoteCallServerDispatcher serverController(List<IController> controllers) {
//		logger.info("注册[RpcDispacher]服务");
//		DefaultRemoteCallServerDispatcher controller = new DefaultRemoteCallServerDispatcher();
//		try {	
//			controller.initialize(controllers);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("注册[DefaultConnectController]服务失败, 异常:", e);
//		}
//		return controller;
//	}
	
//	/**
//	 * RPC客户端服务
//	 * @return
//	 */
//	@Bean
//	public DefaultRemoteCallClientDispatcher rpcController(List<IResponseCallback<? extends AbstractProtocol>> callbacks) {
//		logger.info("注册[DefaultRemoteCallController]服务, size:{}", callbacks.size());
//		DefaultRemoteCallClientDispatcher controller = new DefaultRemoteCallClientDispatcher();
//		try {
//			controller.initialize(callbacks);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("注册[DefaultRemoteCallController]服务失败, 异常:", e);
//		}
//		return controller;
//	}
	
	/*
	 * RPC客户端服务
	 * @return
	 */
	@Bean
	public RpcDispatcher rpcController(List<IRpcController> callbacks) {
		logger.info("注册[DefaultRpcDispatcher]服务, size:{}", callbacks.size());
		RpcDispatcher controller = new RpcDispatcher();
		try {
			controller.initialize(callbacks);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册[DefaultRpcDispatcher]服务失败, 异常:", e);
		}
		return controller;
	}
}
