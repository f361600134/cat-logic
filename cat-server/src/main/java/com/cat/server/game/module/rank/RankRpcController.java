package com.cat.server.game.module.rank;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.api.ProtocolId;
import com.cat.api.module.rank.proto.PBRank;
import com.cat.api.module.rank.proto.PBRankList;
import com.cat.api.module.rank.proto.ReqCoverRankInfo;
import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IRpcController;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankTypeEnum;

@Controller
public class RankRpcController implements IRpcController{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private RankService rankService;
	
	
	@Rpc(value= ProtocolId.ReqCoverRankInfo)
	public void reqCoverRankInfo(ISession session, ReqCoverRankInfo req) {
		List<Rank> ranks = new ArrayList<>();
		for (PBRankList rankList : req.getRankList()) {
			
			for (PBRank pbRank : rankList.getRankInfos()) {
				Rank rank = new Rank(pbRank.getCurServerId(), 
						pbRank.getRankType(), 
						pbRank.getUniqueId(), 
						pbRank.getFirstValue(),
						pbRank.getSecondValue(),
						pbRank.getThirdValue(),
						pbRank.getCreateTime());
				ranks.add(rank);
			}
			rankService.reqCoverRankInfo(rankList.getRankType(), ranks);
			logger.info("收到覆蓋game跨服排行榜数据消息, req:{}, 当前排行榜数据:{}", req,rankService.getRankList(RankTypeEnum.POWER, 5));
		}
	}

}
