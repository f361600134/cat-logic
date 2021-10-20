package com.cat.server.game.module.rank.type.impl;

import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.proto.PBRankDtoBuilder;
import com.cat.server.game.module.rank.type.AbstractRankType;

/**
 * 默认排行类型
 * @author Jeremy
 */
public class DefaultRankType extends AbstractRankType{

//	@Override
//	public int rankType() {
//		return RankTypeEnum.DEFAULT.getConfigId();
//	}

	public DefaultRankType(int rankType) {
		super(rankType);
	}

	/**
	 * 从对应模块中获取到排行榜对比的数据, 然后构建一个临时排行对象
	 */
	@Override
	public PBRankDto buildRankDto(long playerId) {
		PBRankDtoBuilder dtoBuilder = PBRankDtoBuilder.newInstance();
		dtoBuilder.setUniqueId(playerId);
		//FIXME
		//获取到影子对象
		dtoBuilder.setValue(0L);
		//构建排行榜玩家数据
		dtoBuilder.setPlayerProfile(null);
		return dtoBuilder.build();
	}

}
