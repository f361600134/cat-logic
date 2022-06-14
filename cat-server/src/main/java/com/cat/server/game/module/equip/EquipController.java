package com.cat.server.game.module.equip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBEquip.ReqEquipInfo;
import com.cat.server.game.data.proto.PBEquip.ReqEquipPunching;
import com.cat.server.game.data.proto.PBEquip.ReqEquipUpgrade;
import com.cat.server.game.data.proto.PBEquip.ReqTakeoutEquip;
import com.cat.server.game.data.proto.PBEquip.ReqWearEquip;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.equip.proto.RespEquipInfoBuilder;
import com.cat.server.game.module.equip.proto.RespEquipPunchingBuilder;
import com.cat.server.game.module.equip.proto.RespEquipUpgradeBuilder;
import com.cat.server.game.module.equip.proto.RespTakeoutEquipBuilder;
import com.cat.server.game.module.equip.proto.RespWearEquipBuilder;
import com.cat.server.game.module.player.IPlayerService;

/**
 * Equip控制器
 */
@Controller
public class EquipController {
	
	private static final Logger log = LoggerFactory.getLogger(EquipController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private EquipService equipService;
	
	
	/*
	*请求装备加工
	*/
	@Cmd(value = PBProtocol.ReqEquipUpgrade_VALUE)
	public void reqEquipUpgrade(ISession session, ReqEquipUpgrade req) {
		long playerId = session.getUserData();
		RespEquipUpgradeBuilder ack = RespEquipUpgradeBuilder.newInstance();
		ErrorCode code = equipService.reqEquipUpgrade(playerId, req, ack);
		ack.setErrorCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
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
	
	/*
	*请求脱下装备
	*/
	@Cmd(value = PBProtocol.ReqTakeoutEquip_VALUE)
	public void reqTakeoutEquip(ISession session, ReqTakeoutEquip req) {
		long playerId = session.getUserData();
		RespTakeoutEquipBuilder ack = RespTakeoutEquipBuilder.newInstance();
		ErrorCode code = equipService.reqTakeoutEquip(playerId, req, ack);
		ack.setErrorCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求装备打孔
	*/
	@Cmd(value = PBProtocol.ReqEquipPunching_VALUE)
	public void reqEquipPunching(ISession session, ReqEquipPunching req) {
		long playerId = session.getUserData();
		RespEquipPunchingBuilder ack = RespEquipPunchingBuilder.newInstance();
		ErrorCode code = equipService.reqEquipPunching(playerId, req, ack);
		ack.setErrorCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求穿戴装备
	*/
	@Cmd(value = PBProtocol.ReqWearEquip_VALUE)
	public void reqWearEquip(ISession session, ReqWearEquip req) {
		long playerId = session.getUserData();
		RespWearEquipBuilder ack = RespWearEquipBuilder.newInstance();
		ErrorCode code = equipService.reqWearEquip(playerId, req, ack);
		ack.setErrorCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	

}
