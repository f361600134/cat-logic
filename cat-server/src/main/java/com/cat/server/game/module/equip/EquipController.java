package com.cat.server.game.module.equip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.core.server.AbstractController;
import com.cat.server.core.server.IModuleService;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.equip.proto.*;
import com.cat.server.game.data.proto.PBEquip.*;

/**
 * Equip控制器
 */
@Controller
public class EquipController extends AbstractController{
	
	private static final Logger log = LoggerFactory.getLogger(EquipController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private EquipService equipService;
	
	/*
	*请求装备信息
	*/
	@Cmd(value = PBProtocol.ReqEquipInfo_VALUE)
	public void reqEquipInfo(ISession session, ReqEquipInfo req) {
		long playerId = session.getUserData();
		RespEquipInfoBuilder ack = RespEquipInfoBuilder.newInstance();
		equipService.reqEquipInfo(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}

	@Override
	public IModuleService getService() {
		return equipService;
	}

}
