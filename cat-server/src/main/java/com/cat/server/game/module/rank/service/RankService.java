package com.cat.server.game.module.rank.service;

import com.cat.server.common.ServerConfig;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankDomain;
import com.cat.server.game.module.rank.domain.RankType;
import com.cat.server.game.module.rank.manager.RankManager;
import com.cat.server.game.module.player.service.IPlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Rank控制器
 */
@Service
public class RankService implements IRankService {
	
	private static final Logger logger = LoggerFactory.getLogger(RankService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private RankManager rankManager;
	
	@Autowired private ServerConfig config;
	
	
	/////////////业务逻辑//////////////////
	
	/**
	 * 更新排行榜事件
	 * @param context
	 */
	public void rankUpdateEvent(RankType rankType, long uniqueId, long value, long value2) {
		RankDomain domain = this.rankManager.getDomain(rankType.getConfigId());
		if (domain == null) {
			logger.info("loading rank error, domain is null");
			return;
		}
		if (value<=0)  return;
		Rank rank = new Rank();
		rank.setCreateTime(System.currentTimeMillis());
		rank.setCurServerId(config.getServerId());
		rank.setUniqueId(uniqueId);
		rank.setFirstValue(value);
		rank.setSecondValue(value2);
		rank.setRankType(rankType.getConfigId());
		try {
			domain.put(rank.getUniqueId(), rank);
		} catch (Exception e) {
			logger.info("rankUpdateEvent rankType:{}, uniqueId:{}, rank:{}", rankType.getConfigId(), uniqueId, rank);
			logger.error("rankUpdateEvent error, e:{}", e);
		}
	}

	/**
	 * 通知更新排行榜通过类型
	 * @param rankType
	 * @return
	 */
	public void responseRankInfo(long playerId, int configId) {
		try {
			RankDomain domain = this.rankManager.getDomain(configId);
			if (domain == null) {
				logger.info("loading rank error, domain is null");
				return;
			}
		} catch (Exception e) {
			logger.error("responseRankInfo rankType:{}, error:{}", configId, e);
		}
	}

	/////////////接口方法////////////////////////
	
	@Override
	public int getRank(RankType rankType, long uniqueId) {
		int ret = -1;
		RankDomain domain = this.rankManager.getDomain(rankType.getConfigId());
		if (domain == null) {
			logger.info("loading rank error, domain is null");
		}
		try {
			ret = domain.getRankByKey(uniqueId);
		} catch (Exception e) {
			logger.info("rankUpdateEvent rankType:{}, uniqueId:{}, rank:{}", rankType.getConfigId(), uniqueId);
			logger.error("rankUpdateEvent error, e:{}", e);
		}
		return ret;
	}
	
}