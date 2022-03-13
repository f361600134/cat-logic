package com.cat.server.game.module.shop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.core.annotation.ModuleController;
import com.cat.server.core.server.AbstractController;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ModuleDefines;
import com.cat.server.game.module.shop.proto.*;
import com.cat.server.game.data.proto.PBShop.*;

/**
 * Shop控制器
 */
@Controller
public class ShopController extends AbstractController{
	
	private static final Logger log = LoggerFactory.getLogger(ShopController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private ShopService shopService;
	
	/*
	*请求商店购买
	*/
	@Cmd(value = PBProtocol.ReqShopBuy_VALUE)
	public void reqShopBuy(ISession session, ReqShopBuy req) {
		long playerId = session.getUserData();
		RespShopBuyBuilder ack = RespShopBuyBuilder.newInstance();
		ErrorCode code = shopService.reqShopBuy(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求商店一键购买
	*/
	@Cmd(value = PBProtocol.ReqShopQuickBuy_VALUE)
	public void reqShopQuickBuy(ISession session, ReqShopQuickBuy req) {
		long playerId = session.getUserData();
		RespShopQuickBuyBuilder ack = RespShopQuickBuyBuilder.newInstance();
		ErrorCode code = shopService.reqShopQuickBuy(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求商店信息
	*/
	@Cmd(value = PBProtocol.ReqShopInfo_VALUE)
	public void reqShopInfo(ISession session, ReqShopInfo req) {
		long playerId = session.getUserData();
		RespShopInfoBuilder ack = RespShopInfoBuilder.newInstance();
		ErrorCode code = shopService.reqShopInfo(playerId, req, ack);
		ack.setCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	@Override
	public int getFactionId() {
		return ModuleDefines.SHOP;
	}

}
