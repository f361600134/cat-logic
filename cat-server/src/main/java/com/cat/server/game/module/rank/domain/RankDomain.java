package com.cat.server.game.module.rank.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.api.module.rank.proto.PBRank;
import com.cat.net.network.base.IProtocol;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IModuleDomain;
import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.RankManager;
import com.cat.server.game.module.rank.type.IRankType;


/**
 * 公共数据, key排行榜类型, value作为排行榜
 * 所有的任务通过common线程去处理
* @author Jeremy
* FIXME 所有的阻塞响应,改为异步回调的方式处理, 或者通过锁的方式去处理, 最好的解决方式应该还是acotr(akka),reactor的方式实现
* FIXME 20211020 尝试第一次修改线程模型, 在现在基础的线程模型增加异步回调, 尝试失败, 原因:
* 加了回调之后的, 没有办法在同一个线程内去执行相同模块的任务
* @change 20220421 修改排行榜实现, 原本排行榜不使用活动. 修改其实现, 适用于活动排行
*/
public class RankDomain implements IModuleDomain<Integer, Rank>, IRankType{

	private static final Logger log = LoggerFactory.getLogger(RankDomain.class);
	
	private IRankType rankType;
	
	/**
	 * 排行榜数据转内部的pb列表对象
	 * @return
	 */
	public List<PBRank> toInnerProto(){
		List<PBRank> pbRanks = new ArrayList<>();
		try {
			for (Rank rank: getRankInfo()) {
				pbRanks.add(rank.toInnerProto());
			}
		} catch (Exception e) {
			log.error("toInnerProto error, e:", e);
		}
		return pbRanks;
	}
	
	@Override
	public Integer getId() {
		return rankType.rankTypeEnum().getConfigId();
	}

	@Override
	public Class<Rank> getBasePoClazz() {
		return Rank.class;
	}
	
	@Override
	public void initData(Integer id, List<Rank> ranks) {
		if (ranks == null || ranks.isEmpty()) {
			return;
		}
		RankManager rankManager = SpringContextHolder.getBean(RankManager.class);
		this.rankType = rankManager.getRankType(id);
		//this.rankType = RankTypeEnum.getRankType(id).newRankType();
		this.onInit(ranks);
	}

	@Override
	public void initData(Integer id) {
		RankManager rankManager = SpringContextHolder.getBean(RankManager.class);
		this.rankType = rankManager.getRankType(id);
	}

	@Override
	public RankTypeEnum rankTypeEnum() {
		return rankType.rankTypeEnum();
	}

	@Override
	public void onInit(Collection<Rank> ranks) {
		this.rankType.onInit(ranks);
	}

	@Override
	public void onRefresh(Rank rank) {
		this.rankType.onRefresh(rank);
	}

	@Override
	public int getRanking(long uniqueId) {
		return this.rankType.getRanking(uniqueId);
	}

	@Override
	public Collection<Rank> getRankInfo() {
		return this.rankType.getRankInfo();
	}

	@Override
	public PBRankDto buildRankDto(Rank rank) {
		return this.rankType.buildRankDto(rank);
	}

	@Override
	public IProtocol buildProtocol(Collection<Rank> ranks, long uniqueId) {
		return this.rankType.buildProtocol(ranks, uniqueId);
	}

	@Override
	public Collection<Rank> subRankInfo(int fromRank, int toRank) {
		return this.rankType.subRankInfo(fromRank, toRank);
	}

	@Override
	public void onRefresh(Map<Long, Rank> rankMap) {
		this.rankType.onRefresh(rankMap);
	}
	
}

