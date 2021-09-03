package com.cat.battle.service.module.player;

import org.springframework.stereotype.Controller;

import com.cat.battle.service.module.other.AckLoginResp;
import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IController;
import com.cat.server.game.data.proto.PBLogin.ReqLogin;

@Controller
public class PlayerController implements IController {
	
	@Cmd(value=100001, mustLogin= false)
	public void login(ISession session, ReqLogin req) {
		com.cat.battle.service.module.other.ReqLoginBuilder reqqqq = com.cat.battle.service.module.other.ReqLoginBuilder.newInstance();
		reqqqq.build(req.toByteArray());
		
		System.out.println("======>"+reqqqq.getUserName());
		
		String userName = req.getUserName();
		AckLoginResp resp = AckLoginResp.newInstance(); 
		resp.setCode(0);
		resp.setSeq(1);
		resp.setStatus(1);
		session.push(resp);
		System.out.println("模拟收到RPC请求, 响应成功! 请求数据:"+userName);
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
