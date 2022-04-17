package com.cat.server.game.module.pet;

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
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.pet.proto.*;
import com.cat.server.game.data.proto.PBPet.*;

/**
 * Pet控制器
 */
@Controller
public class PetController extends AbstractController{
	
	private static final Logger log = LoggerFactory.getLogger(PetController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private PetService petService;
	
	/*
	*请求宠物升级
	*/
	@Cmd(value = PBProtocol.ReqPetLevelup_VALUE)
	public void reqPetLevelup(ISession session, ReqPetLevelup req) {
		long playerId = session.getUserData();
		RespPetLevelupBuilder ack = RespPetLevelupBuilder.newInstance();
		ErrorCode code = petService.reqPetLevelup(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*宠物资质鉴定
	*/
	@Cmd(value = PBProtocol.ReqPetIdentify_VALUE)
	public void reqPetIdentify(ISession session, ReqPetIdentify req) {
		long playerId = session.getUserData();
		RespPetIdentifyBuilder ack = RespPetIdentifyBuilder.newInstance();
		ErrorCode code = petService.reqPetIdentify(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求宠物激活
	*/
	@Cmd(value = PBProtocol.ReqPetActive_VALUE)
	public void reqPetActive(ISession session, ReqPetActive req) {
		long playerId = session.getUserData();
		RespPetActiveBuilder ack = RespPetActiveBuilder.newInstance();
		ErrorCode code = petService.reqPetActive(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}

	@Override
	public IModuleService getService() {
		return petService;
	}
	

}
