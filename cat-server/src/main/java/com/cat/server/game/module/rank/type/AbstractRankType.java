package com.cat.server.game.module.rank.type;

import java.util.Collection;

import com.cat.net.network.base.IProtocol;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.proto.PBRankDtoBuilder;
import com.cat.server.game.module.rank.proto.RespRankInfoBuilder;
import com.cat.server.game.module.shadow.IShadowService;
import com.cat.server.game.module.shadow.domain.Shadow;

/**
 * 抽象的排行榜类型
 * @author Jeremy
 */
public abstract class AbstractRankType implements IRankType {
	
	/**
	 * 接口类,实例化时注入
	 */
	protected IShadowService shadowService;
	/**
	 * 排行榜类型
	 */
	private final int rankType;
	
	public AbstractRankType(int rankType) {
		super();
		this.rankType = rankType;
		this.shadowService = SpringContextHolder.getBean(IShadowService.class);
	}

	public int getRankType() {
		return rankType;
	}

	/**
	 * 构建排行榜消息
	 * @return
	 */
	public IProtocol buildProtocol(Collection<Rank> rankList, long playerId) {
		RespRankInfoBuilder builder = RespRankInfoBuilder.newInstance();
		builder.setRankType(getRankType());
		//从排行榜里面获取到的排行数据构建成排行榜对象
		//并且构建出自己的排行信息
		PBRankDto selfDto = null;
		for (Rank rank : rankList) {
			PBRankDto rankDto = buildRankDto(rank);
			if (rankDto == null) 
				continue;
			
			builder.addRankDtos(rankDto);
			if (selfDto == null && rank.getUniqueId() == playerId) {
				selfDto = rankDto;
			}
		}
		//如果自己的排行信息为null,则拿到模块数据,构建排行信息
		if (selfDto == null) {
			selfDto = buildRankDto(playerId);
		}
		builder.setSelfRankDto(selfDto);
		return builder;
	}
	
	@Override
	public PBRankDto buildRankDto(Rank rank) {
		Shadow shadow = shadowService.get(rank.getUniqueId());
		if (shadow == null) {
			return null;
		}
		PBRankDtoBuilder dtoBuilder = PBRankDtoBuilder.newInstance();
		dtoBuilder.setUniqueId(rank.getUniqueId());
		dtoBuilder.setValue(rank.getFirstOrder());
		//FIXME 这里从玩家影子对象里面获取到玩家信息, 丢给排行榜
		dtoBuilder.setPlayerProfile(shadow.toProto());
		return dtoBuilder.build();
	}
	
	/**
	 * 根据玩家的模块信息,构建临时排行对象
	 * @return
	 */
	public abstract PBRankDto buildRankDto(long playerId);

}
