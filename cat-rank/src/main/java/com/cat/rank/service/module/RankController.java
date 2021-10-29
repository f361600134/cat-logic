package com.cat.rank.service.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.api.ProtocolId;
import com.cat.api.module.rank.proto.PBRank;
import com.cat.api.module.rank.proto.PBRankList;
import com.cat.api.module.rank.proto.ReqAddDataToRank;
import com.cat.api.module.rank.proto.ReqAddOneDataToRank;
import com.cat.api.module.rank.proto.ReqInitRankInfo;
import com.cat.net.network.annotation.Rpc;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IRpcController;
import com.cat.rank.service.module.domain.Rank;

/**
 * 排行榜跨服入口
 * 
 * @author Jeremy
 */
@Controller
public class RankController implements IRpcController {

	private final Logger logger = LoggerFactory.getLogger(RankController.class);

	@Autowired
	private RankService rankService;

	/**
	 * 请求更新排行榜内容
	 * 
	 * @param session 会话对象
	 * @param req     请求消息
	 */
	@Rpc(value = ProtocolId.ReqInitRankInfo)
	public void reqRankInfo(ISession session, ReqInitRankInfo req) {
		logger.info("收到排行榜rpc请求, 请求[初始化]排行榜数据, req:{}", req);
		for (PBRankList pbRankList : req.getRankInfos()) {
			Map<Long, Rank> rankMap = new HashMap<>();
			for (PBRank pbRank : pbRankList.getRankInfos()) {
				Rank rank = Rank.create(pbRank.getCurServerId(), pbRank.getRankType(), pbRank.getUniqueId(),
						pbRank.getFirstValue(), pbRank.getSecondValue(), pbRank.getThirdValue(),
						pbRank.getCreateTime());
				rankMap.put(rank.getUniqueId(), rank);
			}
			rankService.updateRank(pbRankList.getRankType(), pbRankList.getSorted(), pbRankList.getLimit(), rankMap);
		}
	}

	/**
	 * 请求更新排行榜内容
	 * 
	 * @param session 会话对象
	 * @param req     请求消息
	 */
	@Rpc(value = ProtocolId.ReqAddDataToRank)
	public void reqAddDataToRank(ISession session, ReqAddDataToRank req) {
		logger.info("收到排行榜rpc请求, 请求[更新]排行榜数据, req:{}", req);
		for (PBRankList pbRankList : req.getRankLists()) {
			List<Rank> ranks = new ArrayList<>();
			for (PBRank pbRank : pbRankList.getRankInfos()) {
				Rank rank = Rank.create(pbRank.getCurServerId(), pbRank.getRankType(), pbRank.getUniqueId(),
						pbRank.getFirstValue(), pbRank.getSecondValue(), pbRank.getThirdValue(),
						pbRank.getCreateTime());
				ranks.add(rank);
			}
			// 更新排行榜数据
			rankService.updateRank(pbRankList.getRankType(), ranks);
		}
	}

	/**
	 * 请求更新排行榜内容
	 * 
	 * @param session 会话对象
	 * @param req     请求消息
	 */
	@Rpc(value = ProtocolId.ReqAddOneDataToRank)
	public void reqAddOneDataToRank(ISession session, ReqAddOneDataToRank req) {
		logger.info("收到排行榜rpc请求, 请求[更新]排行榜数据, req:{}", req);
		int rankType = 0;
		List<Rank> ranks = new ArrayList<>();
		for (PBRank pbRank : req.getRankInfos()) {
			rankType = pbRank.getRankType();
			Rank rank = Rank.create(pbRank.getCurServerId(), pbRank.getRankType(), pbRank.getUniqueId(),
					pbRank.getFirstValue(), pbRank.getSecondValue(), pbRank.getThirdValue(), pbRank.getCreateTime());
			ranks.add(rank);
		}
		rankService.updateRank(rankType, ranks);
	}
}
