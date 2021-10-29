package com.cat.rank.service.module.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.api.module.rank.assist.RankComparators;
import com.cat.api.module.rank.proto.PBRank;
import com.cat.api.module.rank.utils.ILeaderboard;
import com.cat.api.module.rank.utils.Leaderboard;

/**
 * 跨服排行榜处理类, 这里不用继承用复合composition
 * @author Jeremy
 */
public class RankDomain {
	
	private static final Logger log = LoggerFactory.getLogger(RankDomain.class);
	
	/**
	 * 排行榜
	 */
	private final ILeaderboard<Long, Rank> leaderboard;
	
	public RankDomain(int rankType, int limit, int sorted) {
		//初始化排行榜
		Comparator<Rank> comparator = RankComparators.getComparator(sorted);
		this.leaderboard = new Leaderboard<>(comparator, limit);
	}
	
	/**
	 * 批量添加排行榜数据
	 */
	public void addRanks(Collection<Rank> ranks) {
		ranks.forEach(rank->{
			leaderboard.put(rank.getUniqueId(), rank);
		});
	}
	
	/**
	 * 批量添加排行榜数据
	 */
	public void addRanks(Map<Long, Rank> rankMap) {
		rankMap.forEach((uniqueId, rank)->{
			leaderboard.put(uniqueId, rank);
		});
	}
	
	/**
	 * 批量添加排行榜数据
	 */
	public Collection<Rank> getRankInfo() {
		try {
			return leaderboard.getRankInfo();
		} catch (Exception e) {
			log.error("getRankInfo error, e:", e);
			return Collections.emptyList();
		}
	}
	
	/**
	 * 排行榜数据转内部的pb列表对象
	 * @return
	 */
	public List<PBRank> toInnerProto(){
		List<PBRank> pbRanks = new ArrayList<>();
		try {
			for (Rank rank: leaderboard.getRankInfo()) {
				pbRanks.add(rank.toInnerProto());
			}
		} catch (Exception e) {
			log.error("toInnerProto error, e:", e);
		}
		return pbRanks;
	}

}
