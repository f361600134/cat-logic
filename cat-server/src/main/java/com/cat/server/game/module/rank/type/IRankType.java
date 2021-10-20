package com.cat.server.game.module.rank.type;

import java.util.Collection;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.domain.Rank;

public interface IRankType{
	
//	/**
//	 * 排行榜类型
//	 * @return
//	 */
//	public int rankType();
	
//	/**
//	 * 
//	 * @return
//	 */
//	public ILeaderboard<Long, Rank> getLeaderboard();
	
	/**
	 * 构建排行榜消息对象
	 * @return
	 */
	public PBRankDto buildRankDto(Rank rank);
	
	/**
	 * 构建排行榜消息
	 * @return
	 */
	public IProtocol buildProtocol(Collection<Rank> rankList, long playerId);

}
