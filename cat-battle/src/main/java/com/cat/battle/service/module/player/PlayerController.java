package com.cat.battle.service.module.player;

import org.springframework.stereotype.Controller;

import com.cat.api.ProtocolId;
import com.cat.api.request.ReqIdentityAuthenticate;
import com.cat.api.response.RespIdentityAuthenticate;
import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IController;

@Controller
public class PlayerController implements IController {
	
//	@Cmd(value=100002, mustLogin= false)
//	public void login(ISession session, ReqLogin req, int seq) {
//		com.cat.battle.service.module.other.ReqLoginBuilder reqqqq = com.cat.battle.service.module.other.ReqLoginBuilder.newInstance();
//		reqqqq.build(req.toByteArray());
//		
//		System.out.println("======>"+reqqqq.getUserName());
//		
//		String userName = req.getUserName();
//		AckLoginResp resp = AckLoginResp.newInstance(); 
//		resp.setCode(0);
//		resp.setSeq(seq);
//		resp.setStatus(1);
//		session.push(resp);
//		System.out.println("模拟收到RPC请求, 响应成功! 请求数据:"+userName);
//	}
	
	@Cmd(value= ProtocolId.ReqIdentityAuthenticate, mustLogin= false)
	public void reqIdentityAuthenticate(ISession session, ReqIdentityAuthenticate req, int seq) {
		System.out.println("======>"+req.getNodeId());
		System.out.println("======>"+req.getNodeType());
		System.out.println("======>"+req.getSecretKey());
		System.out.println("======>"+seq);
		
		RespIdentityAuthenticate resp = new RespIdentityAuthenticate(); 
		resp.setCode(0);
		resp.setSeq(req.getSeq());
		session.push(resp);
		System.out.println("模拟收到RPC请求, 响应成功! 请求数据:");
	}
	
//	@Cmd(value=100001, mustLogin= false)
//	public void login(ISession session, byte[] bytes) {
//		ReqLogin req = ReqLogin.newInstance();
//		req.build(bytes);
//		System.out.println(req.getCode());
//		req.getBuilder().build();
//		System.out.println(req.getCode());
////		String userName = req.getUserName();
//		AckLoginResp resp = AckLoginResp.newInstance(); 
//		resp.setCode(0);
//		resp.setStatus(1);
//		session.push(resp);
////		System.out.println("模拟收到RPC请求, 响应成功! 请求数据:"+userName);
//	}

}
