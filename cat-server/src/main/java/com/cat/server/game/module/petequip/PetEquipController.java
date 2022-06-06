package com.cat.server.game.module.petequip;

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
import com.cat.server.game.module.petequip.proto.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBPetEquip.*;

/**
 * PetEquip控制器
 */
@Controller
public class PetEquipController {
	
	private static final Logger log = LoggerFactory.getLogger(PetEquipController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private PetEquipService petEquipService;
	
	
	/*
	*请求装备信息
	*/
	@Cmd(value = PBProtocol.ReqPetEquipInfo_VALUE)
	public void reqPetEquipInfo(ISession session, ReqPetEquipInfo req) {
		long playerId = session.getUserData();
		RespPetEquipInfoBuilder ack = RespPetEquipInfoBuilder.newInstance();
		petEquipService.reqPetEquipInfo(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求穿戴装备
	*/
	@Cmd(value = PBProtocol.ReqWearPetEquip_VALUE)
	public void reqWearPetEquip(ISession session, ReqWearPetEquip req) {
		long playerId = session.getUserData();
		RespWearPetEquipBuilder ack = RespWearPetEquipBuilder.newInstance();
		petEquipService.reqWearPetEquip(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	

}
