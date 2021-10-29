package com.cat.rank.service.rpc;

import com.cat.api.ProtocolId;
import com.cat.api.module.battle.request.ReqIdentityAuthenticate;
import com.cat.api.module.battle.response.RespIdentityAuthenticate;
import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IRpcController;
import com.cat.net.network.tcp.RpcServerStarter;
import com.cat.net.terminal.IServer;
import com.cat.rank.common.SpringContextHolder;
import com.rpc.common.RpcConfig;
import com.rpc.core.server.RpcNetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController implements IRpcController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private RpcConfig rpcConfig;
	
	
	@Rpc(value= ProtocolId.ReqIdentityAuthenticate, isAuth = false)
	public void reqIdentityAuthenticate(ISession session, ReqIdentityAuthenticate req) {
		
		RpcNetService netService = SpringContextHolder.getBean(RpcNetService.class);
		
		String nodeType = req.getNodeType();
		int nodeId = req.getNodeId();
		String secret =  req.getSecretKey();
		
		//TODO 做验证
		
		//TODO 记录身份OK, 记录信息
		//在连接建立, 网络层缓存session, 但是session是否验证身份, 放在业务层去处理.
		//细节尽量在底层实现, 上层业务尽量不要关注底层.
		IServer server = netService.getRpcServer();
		if (server instanceof RpcServerStarter) {
			RpcServerStarter rpcServer =  (RpcServerStarter)server;
			rpcServer.addSession(nodeType, nodeId, session);
		}
		
		RespIdentityAuthenticate resp = new RespIdentityAuthenticate(); 
		resp.setCode(0);
		resp.setNodeType(rpcConfig.getNodeType());
		resp.setSeq(req.getSeq());
		session.push(resp);
		logger.info("收到RPC请求, 节点id:{}, 节点类型:{}, 返回结果:{}, 序列号:{}", req.getNodeId(), req.getNodeType(), 0, req.getSeq());
	}
	
}
