package com.cat.server.game.module.home;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.home.proto.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;

/**
 * Home控制器
 */
@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private HomeService homeService;
	
	
	/*
	*请求家园信息
	*/
	@Cmd(value = PBProtocol.ReqHomeInfo_VALUE)
	public void reqHomeInfo(ISession session, ReqHomeInfo req) {
		long playerId = session.getUserData();
		RespHomeInfoBuilder ack = RespHomeInfoBuilder.newInstance();
		homeService.reqHomeInfo(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求移除创建布局家具
	*/
	@Cmd(value = PBProtocol.ReqHomeFurnitureRemove_VALUE)
	public void reqHomeFurnitureRemove(ISession session, ReqHomeFurnitureRemove req) {
		long playerId = session.getUserData();
		RespHomeFurnitureRemoveBuilder ack = RespHomeFurnitureRemoveBuilder.newInstance();
		homeService.reqHomeFurnitureRemove(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求布局方案保存
	*/
	@Cmd(value = PBProtocol.ReqHomeSchemeLayoutSave_VALUE)
	public void reqHomeSchemeLayoutSave(ISession session, ReqHomeSchemeLayoutSave req) {
		long playerId = session.getUserData();
		RespHomeSchemeLayoutSaveBuilder ack = RespHomeSchemeLayoutSaveBuilder.newInstance();
		homeService.reqHomeSchemeLayoutSave(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求创建布局家具(家具资源转布局家具)
	*/
	@Cmd(value = PBProtocol.ReqHomeFurnitureCreate_VALUE)
	public void reqHomeFurnitureCreate(ISession session, ReqHomeFurnitureCreate req) {
		long playerId = session.getUserData();
		RespHomeFurnitureCreateBuilder ack = RespHomeFurnitureCreateBuilder.newInstance();
		homeService.reqHomeFurnitureCreate(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求家园家具信息更新
	*/
	@Cmd(value = PBProtocol.ReqHomeFurnitureUpdate_VALUE)
	public void reqHomeFurnitureUpdate(ISession session, ReqHomeFurnitureUpdate req) {
		long playerId = session.getUserData();
		RespHomeFurnitureUpdateBuilder ack = RespHomeFurnitureUpdateBuilder.newInstance();
		homeService.reqHomeFurnitureUpdate(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求布局方案信息
	*/
	@Cmd(value = PBProtocol.ReqHomeSchemeLayoutInfo_VALUE)
	public void reqHomeSchemeLayoutInfo(ISession session, ReqHomeSchemeLayoutInfo req) {
		long playerId = session.getUserData();
		RespHomeSchemeLayoutInfoBuilder ack = RespHomeSchemeLayoutInfoBuilder.newInstance();
		homeService.reqHomeSchemeLayoutInfo(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求家园别院信息
	*/
	@Cmd(value = PBProtocol.ReqHomeBetsuinInfo_VALUE)
	public void reqHomeBetsuinInfo(ISession session, ReqHomeBetsuinInfo req) {
		long playerId = session.getUserData();
		RespHomeBetsuinInfoBuilder ack = RespHomeBetsuinInfoBuilder.newInstance();
		homeService.reqHomeBetsuinInfo(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	

}
