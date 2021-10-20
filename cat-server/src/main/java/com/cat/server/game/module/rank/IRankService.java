package com.cat.server.game.module.rank;

import java.util.Collection;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankTypeEnum;

/**
 * Rank接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IRankService {
	
	/**
	 * 通过排行榜类型获取指定id的排名
	 * @param rankType 排行榜类型
	 * @param uniqueId 唯一id
	 * @return  
	 * @return int  
	 * @date 2021年3月21日下午5:09:20
	 */
	public int getRank(RankTypeEnum rankType, long uniqueId);
	
	/**
	 * 根据排行榜类型获取指定数量的排行数据
	 * @param rankType 排行榜类型
	 * @param limit 数量
	 * @return
	 */
	public Collection<Rank> getRankList(RankTypeEnum rankType, int limit);
	
	/**
	 * 根据排行榜类型和数量构建排行榜消息
	 * @param rankType 排行榜类型
	 * @param limit 数量
	 * @return
	 */
	public IProtocol buildRankList(RankTypeEnum rankType, int limit);

}