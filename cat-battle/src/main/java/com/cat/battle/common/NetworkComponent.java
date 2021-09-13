package com.cat.battle.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cat.net.network.controller.DefaultConnectControllerDispatcher;
import com.cat.net.network.controller.DefaultRemoteCallControllerDispatcher;
import com.cat.net.network.controller.IController;
import com.cat.net.network.rpc.IResponseCallback;
import com.google.protobuf.AbstractMessageLite;
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
	
	/**
	 * 注册游戏服分发处理器
	 * @return
	 */
	@Bean
	public DefaultConnectControllerDispatcher serverController(List<IController> controllers) {
		logger.info("注册[DefaultConnectController]服务");
		DefaultConnectControllerDispatcher controller = new DefaultConnectControllerDispatcher();
		try {	
			controller.initialize(controllers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册[DefaultConnectController]服务失败, 异常:", e);
		}
		return controller;
	}
	
	/**
	 * RPC服务
	 * @return
	 */
	@Bean
	public DefaultRemoteCallControllerDispatcher rpcController(List<IResponseCallback<? extends AbstractMessageLite<?, ?>>> callbacks) {
		logger.info("注册[DefaultRemoteCallController]服务, size:{}", callbacks.size());
		DefaultRemoteCallControllerDispatcher controller = new DefaultRemoteCallControllerDispatcher();
		try {
			controller.initialize(callbacks);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册[DefaultRemoteCallController]服务失败, 异常:", e);
		}
		return controller;
	}
}