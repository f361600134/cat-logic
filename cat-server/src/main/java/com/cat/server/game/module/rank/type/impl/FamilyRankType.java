package com.cat.server.game.module.rank.type.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.family.IFamilyService;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankTypeEnum;
import com.cat.server.game.module.rank.proto.PBRankDtoBuilder;
import com.cat.server.game.module.rank.proto.RespRankInfoBuilder;
import com.cat.server.game.module.rank.type.AbstractRankType;

/**
 * 家族之间的排行榜, 按照贡献度排序
 * @author Jeremy
 *
 */
@Component
public class FamilyRankType extends AbstractRankType {
	
	@Autowired private IFamilyService familyService;
	
	@Override
	public RankTypeEnum rankTypeEnum() {
		return RankTypeEnum.FAMILY;
	}

	/**
	 * 构建家族排行对象
	 */
	@Override
	public PBRankDto buildRankDto(Rank rank) {
		Family family = familyService.getFamilyByFamilyId(rank.getUniqueId());
		if (family == null) {
			return null;
		}
		PBRankDtoBuilder dtoBuilder = PBRankDtoBuilder.newInstance();
		dtoBuilder.setUniqueId(rank.getUniqueId());
		dtoBuilder.setValue(rank.getFirstOrder());
		// TODO 这里把家族对象序列化为协议对象
//		dtoBuilder.setPlayerProfile(shadow.toProto());
		return dtoBuilder.build();
	}

	@Override
	public IProtocol buildProtocol(Collection<Rank> ranks, long playerId) {
		RespRankInfoBuilder builder = RespRankInfoBuilder.newInstance();
		builder.setRankType(this.rankTypeEnum().getConfigId());
		// 从排行榜里面获取到的排行数据构建成排行榜对象
		// 并且构建出自己的排行信息
		long familyId = familyService.getPlayerFamilyId(playerId);
		PBRankDto selfDto = null;
		for (Rank rank : ranks) {
			PBRankDto rankDto = buildRankDto(rank);
			if (rankDto == null) {
				continue;
			}
			builder.addRankDtos(rankDto);
			if (selfDto == null && familyId != 0 && rank.getUniqueId() == familyId) {
				selfDto = rankDto;
			}
		}
		// 如果自己家族的排行信息为null,则拿到模块数据,构建排行信息
		if (selfDto == null && familyId != 0) {
			selfDto = buildRankDto(familyId);
			builder.setSelfRankDto(selfDto);
		}
		return builder;
	}

	/**
	 * 根据家族唯一id构建排行榜对象
	 * @param uniqueId
	 * @return
	 */
	protected PBRankDto buildRankDto(long uniqueId) {
		Family family = familyService.getFamilyByFamilyId(uniqueId);
		if (family == null) {
			return null;
		}
		PBRankDtoBuilder dtoBuilder = PBRankDtoBuilder.newInstance();
		dtoBuilder.setUniqueId(uniqueId);
		dtoBuilder.setValue(family.getLevel());
		// TODO 这里把家族对象序列化为协议对象
//		dtoBuilder.setPlayerProfile(shadow.toProto());
		return dtoBuilder.build();
	}
}
