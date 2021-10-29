package com.cat.server.game.module.rank;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.api.module.rank.proto.PBRankList;
import com.cat.api.module.rank.proto.ReqInitRankInfo;
import com.cat.net.network.base.IProtocol;
import com.cat.net.network.client.RpcClientStarter;
import com.cat.server.common.ServerConfig;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigRank;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankDomain;
import com.cat.server.game.module.rank.domain.RankTypeEnum;
import com.rpc.core.client.RequesterManager;

/**
 * Rank控制器
 */
@Service
class RankService implements IRankService {
	
	private static final Logger logger = LoggerFactory.getLogger(RankService.class);
	
	@Autowired private RankManager rankManager;
	
	@Autowired private ServerConfig config;

	@Autowired private RequesterManager requesterManager;
	
	/////////////业务逻辑//////////////////
	/**
	 * 更新排行榜事件<br>
	 * 如果当前排行榜是跨服排行, 那么入榜成功后, 更新排行数据至跨服排行
	 * @param rankType 排行榜类型
	 * @param uniqueId 唯一id
	 * @param value 第一排序值
	 * @param value2 第二排序值
	 */
	public void rankUpdateEvent(RankTypeEnum rankType, long uniqueId, long value, long value2) {
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
	 * rpc身份验证成功事件,跨服连接成功
	 */
	public void rpcIdentityAuthenticateSuccessEvent(String nodeType)  {
		RpcClientStarter client = requesterManager.getClient(nodeType);
		if(client == null) {
			logger.info("没有找到合适的节点, 节点类型{}", nodeType);
			return;
		}
		//协议对象
		ReqInitRankInfo req = ReqInitRankInfo.create();
		for (RankTypeEnum rankType : RankTypeEnum.values()){
			ConfigRank config = ConfigManager.getInstance().getConfig(ConfigRank.class, rankType.getConfigId());
			//如果不是跨服排行
			if (config.getCross() != 1){
				continue;
			}
			RankDomain domain = this.rankManager.getDomain(rankType.getConfigId());
			if (domain == null){
				continue;
			}
			PBRankList rankList = new PBRankList();
			rankList.setRankType(rankType.getConfigId());
			rankList.setSorted(rankType.getSorted());
			rankList.setLimit(config.getLimit());
			rankList.setRankType(rankType.getConfigId());
			rankList.setRankInfos(domain.toInnerProto());
			req.addRankList(rankList);
		}
		//请求通知排行榜服务器
		client.sendMessage(req);
	}

	/////////////接口方法////////////////////////
	
	@Override
	public int getRank(RankTypeEnum rankType, long uniqueId) {
		int ret = -1;
		if (rankType == null) {
			return ret;
		}
		RankDomain domain = this.rankManager.getDomain(rankType.getConfigId());
		if (domain == null) {
			logger.info("loading rank error, domain is null");
		}
		try {
			ret = domain.getRankByKey(uniqueId);
		} catch (Exception e) {
			logger.info("getRank error. rankType:{}, uniqueId:{}, rank:{}", rankType.getConfigId(), uniqueId, ret);
			logger.error("getRank error, e:{}", e);
		}
		return ret;
	}
	
	@Override
	public Collection<Rank> getRankList(RankTypeEnum rankType, int limit) {
		if (rankType == null) {
			return Collections.emptyList();
		}
		RankDomain domain = this.rankManager.getDomain(rankType.getConfigId());
		if (domain == null) {
			return Collections.emptyList();
		}
		try {
			return domain.subRankInfo(0, limit);
		} catch (Exception e) {
			logger.error("getRankList error, e:", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public IProtocol buildRankList(RankTypeEnum rankType, int limit) {
		if (rankType == null) {
			return null;
		}
		RankDomain domain = this.rankManager.getDomain(rankType.getConfigId());
		if (domain == null) {
			return null;
		}
		Collection<Rank> ranks = Collections.emptyList();
		try {
			ranks = domain.subRankInfo(0, limit);
		} catch (Exception e) {
			logger.error("buildRankList error, e:", e);
		}
		IProtocol protocol = domain.buildProtocol(ranks, limit);
		return protocol;
	}
	
}