package com.cat.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cat.net.LocalNetService;
import com.cat.net.http.controller.DefaultHttpController;
import com.cat.net.http.process.RequestProcessor;
import com.cat.net.network.process.ControllerDispatcher;
import com.cat.server.core.lifecycle.Lifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.core.server.GameServerController;

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
	public GameServerController gameServerController() {
		logger.info("注册[GameServerController]服务");
		return new GameServerController();
	}
	
	/**
	 * 注册游戏协议控制器
	 * @return
	 */
	@Bean
	public ControllerDispatcher controllerProcessor() {
		logger.info("注册[ControllerDispatcher]服务");
		return new ControllerDispatcher();
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
	
//	/**
//	 * 注册tcp服务
//	 * @return
//	 */
//	@Bean
//	public TcpServerStarter tcpServerStarter(IServerController gameController, NetConfig config) {
//		return new TcpServerStarter(gameController, config.getServerIp(), config.getTcpPort());
//	}
//	
//	/**
//	 * 注册webSocket服务
//	 * @return
//	 */
//	@Bean
//	public WebSocketServerStarter webSocketServerStarter(IServerController gameController, NetConfig config) {
//		return new WebSocketServerStarter(gameController, config.getServerIp(), config.getWebscoketPort());
//	}
	
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
