package com.cat.robot.common.conpoent;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cat.net.network.controller.DefaultConnectController;
import com.cat.net.network.controller.IController;

/**
 * 网络组件 可以根据需要注册
 * 
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
	public DefaultConnectController serverController(List<IController> controllers) {
		logger.info("注册[DefaultConnectController]服务");
		DefaultConnectController controller = new DefaultConnectController();
		try {
			controller.initialize(controllers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册[DefaultConnectController]服务失败, 异常:", e);
		}
		return controller;
	}

//	/**
//	 * 注册游戏协议控制器
//	 * 
//	 * @return
//	 */
//	@Bean
//	public CommanderProtocolMapper controllerProcessor() {
//		logger.info("注册[ControllerProcessor]服务");
//		return new CommanderProtocolMapper();
//	}

}
