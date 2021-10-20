package com.cat.server.game.module.rank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.net.network.base.IProtocol;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.base.ConfigRank;
import com.cat.server.game.data.proto.PBRank.ReqRankInfo;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.rank.domain.RankTypeEnum;
import com.cat.server.game.module.rank.proto.RespRankInfoBuilder;

@Service
public class PlayerRankService {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerRankService.class);
	
	@Autowired private IPlayerService playerService;
	@Autowired private RankService rankService;

	/**
	 * 请求排行榜信息
	 * @param playerId
	 * @param req
	 * @param ack
	 */
	public ErrorCode reqRankInfo(long playerId, ReqRankInfo req, RespRankInfoBuilder ack) {
		int rankType = req.getRankType();
		if (rankType <= 0) {
			return ErrorCode.INVALID_PARAM;
		}
		RankTypeEnum rankTypeEnum = RankTypeEnum.getRankType(rankType);
		if (rankTypeEnum == null) {
			return ErrorCode.INVALID_PARAM;
		}
		ConfigRank config = ConfigManager.getInstance().getConfig(ConfigRank.class, rankType);
		int showNum = config.getShowNum();
		IProtocol protocol = rankService.buildRankList(rankTypeEnum, showNum);
		if (protocol == null) {
			log.info("reqRankInfo error, the result of buildRankList is null.");
			return ErrorCode.UNKNOWN_ERROR;
		}
		//返回玩家排行榜信息
		playerService.sendMessage(playerId, protocol);
		//返回错误码
		return ErrorCode.SUCCESS;
	}

}
