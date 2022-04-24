package com.cat.server.game.module.rank.type;

import java.util.Collection;
import java.util.Map;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankTypeEnum;

public interface IRankType {
	
	/**
	 * 排行榜类型
	 * @return 返回排行榜枚举类型
	 */
	public RankTypeEnum rankTypeEnum();
	
	/**
	 * 当初始化排行榜, 先清掉容器内的所有排行数据, 然后初始化集合内的排行数据
	 * @param ranks 初始化排行列表
	 */
	public void onInit(Collection<Rank> ranks);
	
	/**
	 * 当排行榜刷新,新入榜即刷新
	 * @param rank 新增的/修改的排行榜对象
	 */
	public void onRefresh(Rank rank);
	
	/**
	 * 当批量更新排行榜, 大批量的数据更改, 直接插入排行榜进行重新排行
	 * @param ranks 待更新排行榜列表
	 */
	public void onRefresh(Map<Long, Rank> rankMap);
	
	/**
	 * 根据唯一id获取到排名
	 * @param uniqueId
	 * @return 返回排名
	 */
	public int getRanking(long uniqueId);
	
	/**
	 * 获取当前排行榜信息
	 * @return 返回排行榜列表
	 */
	public Collection<Rank> getRankInfo();
	
	/**
	 * 获取当前排行榜信息
	 * @return 返回排行榜列表
	 */
	public Collection<Rank> subRankInfo(int fromRank, int toRank);
	
	/**
	 * 构建排行榜消息对象
	 * @param rank 排行榜对象转排行榜协议对象
	 * @return 返回排行榜协议对象
	 */
	public PBRankDto buildRankDto(Rank rank);
	
	/**
	 * 构建排行榜消息
	 * @return 返回排行榜列表消息
	 */
	public IProtocol buildProtocol(Collection<Rank> ranks, long playerId);

}
