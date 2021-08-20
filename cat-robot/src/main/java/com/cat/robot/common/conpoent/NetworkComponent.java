package com.cat.robot.common.conpoent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cat.net.network.controller.DefaultConnectController;
import com.cat.net.network.process.ControllerDispatcher;

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
	public DefaultConnectController gameServerController() {
		logger.info("注册[GameServerController]服务");
		return new DefaultConnectController();
	}
	
	/**
	 * 注册游戏协议控制器
	 * @return
	 */
	@Bean
	public ControllerDispatcher controllerProcessor() {
		logger.info("注册[ControllerProcessor]服务");
		return new ControllerDispatcher();
	}
	
}
