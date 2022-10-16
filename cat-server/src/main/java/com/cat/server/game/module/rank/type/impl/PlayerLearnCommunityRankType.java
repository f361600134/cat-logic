package com.cat.server.game.module.rank.type.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.activityoperation.learncommunity.ILearnCommunityService;
import com.cat.server.game.module.activityoperation.learncommunity.LearnCommunityActivityType;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunity;
import com.cat.server.game.module.rank.domain.RankTypeEnum;
import com.cat.server.game.module.rank.proto.PBRankDtoBuilder;
import com.cat.server.game.module.rank.type.AbstractActivityRankType;
import com.cat.server.game.module.shadow.domain.Shadow;

/**
 * 玩家研习社排行类型
 * @author Jeremy
 */
@Component
public class PlayerLearnCommunityRankType extends AbstractActivityRankType<LearnCommunityActivityType> {
	
	@Autowired private ILearnCommunityService service;

	@Override
	public RankTypeEnum rankTypeEnum() {
		return RankTypeEnum.LearnCommunityRank;
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
		//获取到研习社活动数据
		LearnCommunity learnConmunity = service.getLearnCommunity(playerId);
		if (learnConmunity == null) {
			return null;
		}
		PBRankDtoBuilder dtoBuilder = PBRankDtoBuilder.newInstance();
		dtoBuilder.setUniqueId(playerId);
		//获取到影子对象
		dtoBuilder.setValue(learnConmunity.getLevel());
		//构建排行榜玩家数据
		dtoBuilder.setPlayerProfile(shadow.toProto());
		return dtoBuilder.build();
	}

}
