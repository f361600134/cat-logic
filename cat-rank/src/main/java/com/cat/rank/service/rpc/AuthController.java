package com.cat.rank.service.rpc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.api.ProtocolId;
import com.cat.api.module.battle.request.ReqIdentityAuthenticate;
import com.cat.api.module.battle.response.RespIdentityAuthenticate;
import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IRpcController;
import com.cat.net.network.tcp.RpcServerStarter;
import com.cat.rank.service.helper.ErrorCode;
import com.cat.rank.utils.MD5;
import com.rpc.common.RpcConfig;

@Controller
public class AuthController implements IRpcController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private RpcConfig rpcConfig;
	
	
	@Rpc(value= ProtocolId.ReqIdentityAuthenticate, isAuth = false)
	public void reqIdentityAuthenticate(ISession session, ReqIdentityAuthenticate req) {
		
		String nodeType = req.getNodeType();
		int nodeId = req.getNodeId();
		String remoteSecret =  req.getSecretKey();
		
		//做验证
		ErrorCode errorCode = ErrorCode.SUCCESS;
		String desc = String.valueOf(nodeId).concat(nodeType);
		String localSecretKey = MD5.digest(desc, true);
		
		logger.info("reqIdentityAuthenticate, nodeType:{}, nodeId:{}, remoteSecret:{}, localSecretKey:{}", nodeType, nodeId, remoteSecret, localSecretKey);
		if (!StringUtils.equals(localSecretKey, remoteSecret)) {
			//身份认证失败, 返回错误给连接端
			errorCode = ErrorCode.AUTHENTICATION_FAILED;
		}
		
		if (errorCode.isSuccess()) {
			//记录身份OK, 记录信息
			//在连接建立, 网络层缓存session, 但是session是否验证身份, 放在业务层去处理.
			//细节尽量在底层实现, 上层业务尽量不要关注底层.
			RpcServerStarter rpcServer = session.getUserData();
			rpcServer.addSession(nodeType, nodeId, session);
		}
		
		RespIdentityAuthenticate resp = new RespIdentityAuthenticate(); 
		resp.setCode(errorCode.getCode());
		resp.setNodeType(rpcConfig.getNodeType());
		resp.setSeq(req.getSeq());
		session.push(resp);
		logger.info("收到RPC请求, 节点id:{}, 节点类型:{}, 返回结果:{}, 序列号:{}", req.getNodeId(), req.getNodeType(), 0, req.getSeq());
	}
	
}
