package com.cat.server.game.module.rank.type;

import com.cat.api.module.rank.utils.ILeaderboard;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankTypeEnum;

public interface IRankNewType{
	
	/**
	 * 排行榜类型
	 * @return
	 */
	public RankTypeEnum rankTypeEnum();
	
	/**
	 * 
	 * @return
	 */
	public ILeaderboard<Long, Rank> getLeaderboard();
	
	/**
	 * 构建排行榜消息对象
	 * @return
	 */
	public PBRankDto buildRankDto(Rank rank);
	
	/**
	 * 构建排行榜消息
	 * @return
	 */
	public IProtocol buildProtocol(long playerId);

}
