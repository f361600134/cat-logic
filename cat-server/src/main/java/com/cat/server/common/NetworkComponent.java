package com.cat.server.common;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cat.api.module.battle.request.ReqIdentityAuthenticate;
import com.cat.api.module.battle.response.RespIdentityAuthenticate;
import com.cat.net.LocalNetService;
import com.cat.net.http.controller.DefaultHttpController;
import com.cat.net.http.process.RequestProcessor;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.net.network.base.IProtocol;
import com.cat.net.network.controller.DefaultConnectControllerDispatcher;
import com.cat.net.network.controller.IController;
import com.cat.net.network.controller.IRpcController;
import com.cat.net.network.rpc.IResponseCallback;
import com.cat.net.network.rpc.IRpcAuthenticationListenable;
import com.cat.net.network.controller.DefaultRpcDispatcher;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.rpc.callback.ReqIdentityAuthenticateCallback;
import com.cat.server.utils.MD5;
import com.rpc.common.RpcConfig;

/**
 * 网络组件
 * 可以根据需要注册
 * @author Jeremy
 */
@Configuration
public class NetworkComponent implements ILifecycle{
	
	private static final Logger logger = LoggerFactory.getLogger(NetworkComponent.class);
	
	/**
	 * 网络服务
	 */
	@Autowired private LocalNetService localNetService;
	
	@Autowired private RpcConfig rpcConfig;
	
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
	 * RPC服务分发器
	 * @return
	 */
	@Bean
	public DefaultRpcDispatcher rpcController(List<IRpcController> controllers) {
		logger.info("注册[DefaultRpcDispatcher]服务, size:{}", controllers.size());
		DefaultRpcDispatcher dispatcher = new DefaultRpcDispatcher();
		try {
			dispatcher.initialize(controllers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册[DefaultRpcDispatcher]服务失败, 异常:", e);
		}
		return dispatcher;
	}
	
	/**
	 * 注入RPC启动后的身份认证监听项
	 * @return
	 */
	@Bean
	public IRpcAuthenticationListenable listenable() {
		return new IRpcAuthenticationListenable() {
			@Override
			public Pair<IProtocol, IResponseCallback<? extends AbstractProtocol>> authentication() {
				//rpc请求方法
				int serverId = rpcConfig.getServerId();
				String nodeType = rpcConfig.getNodeType();
				String desc = String.valueOf(serverId).concat(nodeType);
				String secretKey = MD5.digest(desc, true);
				ReqIdentityAuthenticate req = ReqIdentityAuthenticate.create(serverId, nodeType, secretKey);
				//rpc响应回调
				IResponseCallback<RespIdentityAuthenticate> resp = new ReqIdentityAuthenticateCallback();
				return Pair.of(req, resp);
			}
		};
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
