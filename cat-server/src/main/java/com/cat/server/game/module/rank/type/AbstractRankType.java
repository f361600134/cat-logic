package com.cat.server.game.module.rank.type;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.api.core.task.TokenTaskQueueExecutor;
import com.cat.api.module.rank.utils.ILeaderboard;
import com.cat.api.module.rank.utils.Leaderboard;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigRank;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankTypeEnum;

public abstract class AbstractRankType implements IRankType {

	public final static long WAIT_TIME = TimeUnit.SECONDS.toMillis(1);

	protected Logger logger = LoggerFactory.getLogger(AbstractRankType.class);

	/*** 当前排行榜数据 */
	private ILeaderboard<Long, Rank> leaderboard;

	/**
	 * 公共的线程池处理器
	 */
	@Autowired
	private TokenTaskQueueExecutor defaultExecutor;

	public AbstractRankType() {
		RankTypeEnum rankTypeEnum = this.rankTypeEnum();
		if (rankTypeEnum == null) {
			throw new NullPointerException("rankType error, rankTypeEnum is null");
		}
	}

	@Override
	public void onInit(Collection<Rank> ranks) {
		RankTypeEnum rankTypeEnum = this.rankTypeEnum();
		ConfigRank config = ConfigManager.getInstance().getConfig(ConfigRank.class, rankTypeEnum.getConfigId());
		if (config == null) {
			throw new NullPointerException("load rank error, configId:" + rankTypeEnum.getConfigId());
		}
		// 初始化排行榜
		this.leaderboard = new Leaderboard<>(rankTypeEnum.getComparator(), config.getLimit(), (updates, deletes) -> {
			updates.forEach(r -> {
				r.replace();
			});
			deletes.forEach(r -> {
				r.delete();
			});
		});
		// List转Map
		Map<Long, Rank> map = new HashMap<>();
		ranks.forEach((rank) -> {
			map.put(rank.getId(), rank);
		});
		this.defaultExecutor.submit(0, () -> {
			this.leaderboard.clear();
			this.leaderboard.putAll(map);
		});
	}

	@Override
	public void onRefresh(Rank rank) {
		this.defaultExecutor.submit(0, () -> {
			this.leaderboard.put(rank.getUniqueId(), rank);
		});
	}

	@Override
	public void onRefresh(Map<Long, Rank> rankMap) {
		this.defaultExecutor.submit(0, () -> {
			this.leaderboard.putAll(rankMap);
		});
	}

	/**
	 * 获取当前排行榜信息
	 * 
	 * @return 返回排行榜列表
	 */
	@Override
	public Collection<Rank> subRankInfo(int fromRank, int toRank) {
		Future<Collection<Rank>> future = this.defaultExecutor.submit(0, () -> {
			return this.leaderboard.subRankInfo(fromRank, toRank);
		});
		try {
			return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("subRankInfo error.", e);
		}
		return Collections.emptyList();
	}

	@Override
	public int getRanking(long uniqueId) {
		Future<Integer> future = this.defaultExecutor.submit(0, () -> {
			return this.leaderboard.getRankByKey(uniqueId);
		});
		try {
			return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("getRanking error.", e);
		}
		return 0;
	}

	/**
	 * 获取排行榜快照
	 * 
	 * @return void
	 * @date 2022年4月21日下午11:02:43
	 */
	public Collection<Rank> getRankInfo() {
		Future<Collection<Rank>> future = this.defaultExecutor.submit(0, () -> {
			return this.leaderboard.getRankInfo();
		});
		try {
			return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("getRankInfo error.", e);
			logger.info("");
		}
		return Collections.emptyList();
	}

}
