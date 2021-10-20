package com.cat.server.game.module.rank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBRank.ReqRankInfo;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.rank.proto.RespRankInfoBuilder;

/**
 * 玩家Rank控制器
 */
@Controller
public class PlayerRankController {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerRankController.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired
	private PlayerRankService playeRankService;
	
	/*
	*请求排行榜信息
	*/
	@Cmd(value = PBProtocol.ReqRankInfo_VALUE)
	public void ReqRankInfo(ISession session, ReqRankInfo req) {
		long playerId = session.getUserData();
		RespRankInfoBuilder ack = RespRankInfoBuilder.newInstance();
		ErrorCode errorcode = playeRankService.reqRankInfo(playerId, req, ack);
		//返回错误码给客户端
		playerService.sendMessage(playerId, errorcode.toProto());
	}
	

}
