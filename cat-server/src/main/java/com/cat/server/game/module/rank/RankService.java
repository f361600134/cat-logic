package com.cat.server.game.module.rank;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.api.module.rank.proto.PBRankList;
import com.cat.api.module.rank.proto.ReqAddOneDataToRank;
import com.cat.api.module.rank.proto.ReqInitRankInfo;
import com.cat.net.network.base.IProtocol;
import com.cat.net.network.client.RpcClientStarter;
import com.cat.server.common.ServerConfig;
import com.cat.server.common.ServerConstant;
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
class RankService implements IRankService{
	
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
		//更新入排行榜
		domain.onRefresh(rank);
		
		//FIXME 这里暂时设定为每次必定同步到跨服, 用于模拟测试
		RpcClientStarter client = requesterManager.getClient(ServerConstant.NODE_TYPE_RANK);
		if(client == null) {
			logger.info("没有找到合适的节点, 节点类型{}", ServerConstant.NODE_TYPE_RANK);
			return;
		}
		client.sendMessage(ReqAddOneDataToRank.create(Lists.newArrayList(rank.toInnerProto())));
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
		logger.info("发送排行榜数据:{}", req);
		//请求通知排行榜服务器
		client.sendMessage(req);
	}
	
	/**
	 * 处理覆盖排行榜数据
	 */
	public void reqCoverRankInfo(int rankType, Map<Long, Rank> rankMap) {
		RankDomain domain = this.rankManager.getDomain(rankType);
		if (domain == null){
			logger.info("reqCoverRankInfo error, domain is null");
			return;
		}
		domain.onRefresh(rankMap);
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
			return ret;
		}
		try {
			ret = domain.getRanking(uniqueId);
		} catch (Exception e) {
			logger.info("getRank error. rankType:{}, uniqueId:{}, rank:{}", rankType.getConfigId(), uniqueId, ret, e);
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
			return domain.subRankInfo(1, limit);
		} catch (Exception e) {
			logger.error("getRankList error, e:", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public IProtocol buildRankList(RankTypeEnum rankType, long playerId, int limit) {
		if (rankType == null) {
			return null;
		}
		RankDomain domain = this.rankManager.getDomain(rankType.getConfigId());
		if (domain == null) {
			return null;
		}
		Collection<Rank> ranks = Collections.emptyList();
		try {
			ranks = domain.subRankInfo(1, limit);
		} catch (Exception e) {
			logger.error("buildRankList error, e:", e);
		}
		IProtocol protocol = domain.buildProtocol(ranks, playerId);
		return protocol;
	}

	@Override
	public void removeRankList(RankTypeEnum rankType) {
		if (rankType == null) {
			return;
		}
		this.rankManager.remove(rankType.getConfigId());
	}
	
}