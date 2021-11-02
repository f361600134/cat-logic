package com.cat.server.game.module.rank.type.impl;

import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.domain.RankTypeEnum;
import com.cat.server.game.module.rank.proto.PBRankDtoBuilder;
import com.cat.server.game.module.rank.type.AbstractPlayerRankType;
import com.cat.server.game.module.shadow.domain.Shadow;

/**
 * 玩家其他排行类型
 * @author Jeremy
 */
public class PlayerOtherRankType extends AbstractPlayerRankType {

	public PlayerOtherRankType() {
	}

	@Override
	public int rankType() {
		return RankTypeEnum.OTHER.getConfigId();
	}

	/**
	 * 从对应模块中获取到排行榜对比的数据, 然后构建一个临时排行对象
	 */
	@Override
	public PBRankDto buildRankDto(long playerId) {
		Shadow shadow = shadowService.get(playerId);
		if (shadow == null) {
			return null;
		}
		PBRankDtoBuilder dtoBuilder = PBRankDtoBuilder.newInstance();
		dtoBuilder.setUniqueId(playerId);
		//获取到影子对象
		dtoBuilder.setValue(shadow.getOther().getPower());
		//构建排行榜玩家数据
		dtoBuilder.setPlayerProfile(shadow.toProto());
		return dtoBuilder.build();
	}

}
