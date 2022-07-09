package com.cat.server.game.module.stall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBStall.ReqFinishStall;
import com.cat.server.game.data.proto.PBStall.ReqStallBuy;
import com.cat.server.game.data.proto.PBStall.ReqStallCommoditySearch;
import com.cat.server.game.data.proto.PBStall.ReqStallSearch;
import com.cat.server.game.data.proto.PBStall.ReqStartStall;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.stall.proto.RespFinishStallBuilder;
import com.cat.server.game.module.stall.proto.RespStallBuyBuilder;
import com.cat.server.game.module.stall.proto.RespStallCommoditySearchBuilder;
import com.cat.server.game.module.stall.proto.RespStallSearchBuilder;
import com.cat.server.game.module.stall.proto.RespStartStallBuilder;

/**
 * Stall控制器
 */
@Controller
public class StallController {
	
	private static final Logger log = LoggerFactory.getLogger(StallController.class);
  
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private StallService stallService;
	
	
	/*
	*请求开始摆摊
	*/
	@Cmd(value = PBProtocol.ReqStartStall_VALUE)
	public void reqStartStall(ISession session, ReqStartStall req) {
		long playerId = session.getUserData();
		RespStartStallBuilder ack = RespStartStallBuilder.newInstance();
		ErrorCode code = null;
		try {
			code = stallService.reqStartStall(playerId, req, ack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ack.setErrorCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求结束摆摊
	*/
	@Cmd(value = PBProtocol.ReqFinishStall_VALUE)
	public void reqFinishStall(ISession session, ReqFinishStall req) {
		long playerId = session.getUserData();
		RespFinishStallBuilder ack = RespFinishStallBuilder.newInstance();
//		ErrorCode code = stallService.reqFinishStall(playerId, req, ack);
//		ack.setErrorCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求摊位商品查找
	*/
	@Cmd(value = PBProtocol.ReqStallCommoditySearch_VALUE)
	public void reqStallCommoditySearch(ISession session, ReqStallCommoditySearch req) {
		long playerId = session.getUserData();
		RespStallCommoditySearchBuilder ack = RespStallCommoditySearchBuilder.newInstance();
		//stallService.reqStallCommoditySearch(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求摊位查找
	*/
	@Cmd(value = PBProtocol.ReqStallSearch_VALUE)
	public void reqStallSearch(ISession session, ReqStallSearch req) {
		long playerId = session.getUserData();
		RespStallSearchBuilder ack = RespStallSearchBuilder.newInstance();
		//stallService.reqStallSearch(playerId, req, ack);
		playerService.sendMessage(playerId, ack);
	}
	
	/*
	*请求摆摊购买
	*/
	@Cmd(value = PBProtocol.ReqStallBuy_VALUE)
	public void reqStallBuy(ISession session, ReqStallBuy req) {
		long playerId = session.getUserData();
		RespStallBuyBuilder ack = RespStallBuyBuilder.newInstance();
		ErrorCode code = stallService.reqStallBuy(playerId, req, ack);
		ack.setErrorCode(code.getCode());
		playerService.sendMessage(playerId, ack);
	}

}
