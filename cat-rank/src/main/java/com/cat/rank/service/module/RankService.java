package com.cat.rank.service.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.api.core.task.Task;
import com.cat.api.core.task.impl.CommonTaskExecutor;
import com.cat.api.module.rank.proto.PBRankList;
import com.cat.api.module.rank.proto.ReqCoverRankInfo;
import com.cat.net.network.tcp.RpcServerStarter;
import com.cat.rank.common.ServerConstant;
import com.cat.rank.core.lifecycle.ILifecycle;
import com.cat.rank.service.module.domain.Rank;
import com.cat.rank.service.module.domain.RankDomain;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.rpc.core.server.RpcNetService;

/**
 * 用于需要跨服的排行榜<br>
 * 此排行榜延迟更新设计, 定时任务开始, 把所有缓存的数据, 更新至排行榜, 最后把最新的排行榜数据同步至所有游戏子节点
 */
@Service
public class RankService implements ILifecycle {

	private final Logger logger = LoggerFactory.getLogger(RankService.class);

	@Autowired
	private RankManager rankManager;

	/**
	 * 公共的线程池处理器
	 */
	@Autowired
	private CommonTaskExecutor commonExecutor;

	@Autowired
	private RpcNetService netService;

	/**
	 * key: 排行榜类型 value: 排行榜对象集合
	 */
	private final Multimap<Integer, Rank> updateMap = Multimaps.newMultimap(new ConcurrentHashMap<>(), ArrayList::new);

	/**
	 * 当更新排行榜
	 */
	public void updateRank(int rankType, int sorted, int limit, Map<Long, Rank> rankMap) {
		if (rankMap == null || rankMap.isEmpty()) {
			return;
		}
		commonExecutor.scheduleTask(() -> {
			RankDomain domain = rankManager.getOrCreateDomain(rankType, sorted, limit);
			if (domain == null) {
				logger.info("updateRank error, leaderboard is null, rankType:{}", rankType);
				return;
			}
			try {
				domain.addRanks(rankMap);
				logger.info("当前排行榜数据, ranks:{}, newData:{}", domain.getRankInfo(), rankMap);
			} catch (Exception e) {
				logger.error("updateRank error, e:", e);
			}
		});
	}

	/**
	 * 更新排行榜<br>
	 * 此方法会延迟更新排行榜, 所有游戏子节要更新的数据, 先缓存起来<br>
	 */
	public void updateRank(int rankType, List<Rank> ranks) {
		if (ranks == null || ranks.isEmpty()) {
			return;
		}
		commonExecutor.scheduleTask(() -> {
			Collection<Rank> updateList = updateMap.get(rankType);
			updateList.addAll(ranks);
		});
	}

	/**
	 * 服务启动, 初始化排行榜模块<br>
	 * 
	 * @throws Throwable
	 */
	@Override
	public void start() throws Throwable {
		commonExecutor.scheduleTaskStartAtNextTimeUnit(new UpdateTask(), 1, TimeUnit.MINUTES);
	}

	private class UpdateTask implements Task {
		@Override
		public void execute() throws Exception {
			//没有待更新数据, 跳过本次执行
			if (updateMap.isEmpty()) {
				return;
			}
			List<PBRankList> rankLists = new ArrayList<>();
			for (Integer rankType : updateMap.keySet()) {
				RankDomain domain = rankManager.getDomain(rankType);
				Collection<Rank> updateList = updateMap.get(rankType);
				if (domain == null) {
					logger.info("start error, Can not add new data to domain, domain is null!");
					// 清掉内存数据,防止内存堆积导致溢出
					updateList.clear();
					continue;
				}
				logger.info("====start add new data to domain====, updateMap.size:{}", updateMap);
				domain.addRanks(updateList);
				updateList.clear();
				// 组装协议
				PBRankList rankList = new PBRankList();
				rankList.setRankType(rankType);
				rankList.setRankInfos(domain.toInnerProto());
				rankLists.add(rankList);
			}
			if (rankLists.isEmpty()) {
				return;
			}
			// response data to all of the game nodes.
			ReqCoverRankInfo req = ReqCoverRankInfo.create(rankLists);
			RpcServerStarter server = (RpcServerStarter) netService.getRpcServer();
			server.ask(ServerConstant.NODE_TYPE_GAME, req, null);
		}
	}

}
