package com.cat.server.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cat.net.LocalNetService;
import com.cat.net.http.controller.DefaultHttpController;
import com.cat.net.http.process.RequestProcessor;
import com.cat.net.network.controller.DefaultConnectControllerDispatcher;
import com.cat.net.network.controller.DefaultRemoteCallControllerDispatcher;
import com.cat.net.network.controller.IController;
import com.cat.net.network.rpc.IResponseCallback;
import com.cat.server.core.lifecycle.Lifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.google.protobuf.AbstractMessageLite;

/**
 * 网络组件
 * 可以根据需要注册
 * @author Jeremy
 */
@Configuration
public class NetworkComponent implements Lifecycle{
	
	private static final Logger logger = LoggerFactory.getLogger(NetworkComponent.class);
	
	/**
	 * 网络服务
	 */
	@Autowired private LocalNetService localNetService;
	
	/**
	 * 默认http rest协议分发器
	 * @return
	 */
	@Bean
	public DefaultHttpController httpController() {
		logger.info("注册[DefaultHttpController]服务");
		return new DefaultHttpController();
	}
	
	/**
	 * Request请求处理器
	 * @return
	 */
	@Bean
	public RequestProcessor requestProcessor() {
		logger.info("注册[RequestProcessor]服务");
		return new RequestProcessor();
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
	
	/**
	 * 网络服务
	 * @return
	 */
	@Bean
	public LocalNetService service() {
		logger.info("注册[LocalNetService]服务");
		return new LocalNetService();
	}
	
	@Override
	public void start() throws Throwable {
		localNetService.startup();
	}
	@Override
	public void stop() throws Throwable {
		localNetService.shutdown();
	}
	
	@Override
	public int priority() {
		return Priority.SYSTEM_INITIALIZATION.getPriority();
	}
}
