package com.cat.server.game.module.rank.type;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.api.module.rank.utils.ILeaderboard;
import com.cat.api.module.rank.utils.Leaderboard;
import com.cat.net.network.base.IProtocol;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigRank;
import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankTypeEnum;
import com.cat.server.game.module.rank.proto.PBRankDtoBuilder;
import com.cat.server.game.module.rank.proto.RespRankInfoBuilder;
import com.cat.server.game.module.shadow.IShadowService;
import com.cat.server.game.module.shadow.domain.Shadow;

/**
 * 抽象排行榜<br>
 * 
 * @auth Jeremy
 * @date 2022年4月21日下午10:56:38
 */
public abstract class AbstractRankType implements IRankNewType {

	/**
	 * 接口类,实例化时注入
	 */
	@Autowired
	protected IShadowService shadowService;

	protected Logger logger = LoggerFactory.getLogger(AbstractRankType.class);

	/**
	 * 当前排行榜数据
	 */
	private ILeaderboard<Long, Rank> leaderboard;

	/**
	 * 需要修改的数据, 定时修改 修改的对象集合
	 */
	private Set<Rank> updateMap;
	/**
	 * 删除的排行对象集合
	 */
	private Set<Rank> deleteMap;
	
	
	public AbstractRankType() {
		RankTypeEnum rankTypeEnum = this.rankTypeEnum();
		if (rankTypeEnum == null) {
			throw new NullPointerException("rankType error, rankTypeEnum is null");
		}
		ConfigRank config = ConfigManager.getInstance().getConfig(ConfigRank.class, rankTypeEnum.getConfigId());
		if (config == null) {
			throw new NullPointerException("load rank error, configId:" + rankTypeEnum.getConfigId());
		}
		// 初始化排行榜
		this.leaderboard = new Leaderboard<>(rankTypeEnum.getComparator(), config.getLimit(), (updates, deletes) -> {
			this.updateMap.addAll(updates);
			this.deleteMap.addAll(deletes);
		});
	} 
	
	
	@Override
	public ILeaderboard<Long, Rank> getLeaderboard() {
		return leaderboard;
	}

	/**
	 * 当排行榜变动,外部数据变化导致排行榜数据变动
	 * 
	 * @param rank
	 *            排行对象
	 * @return void
	 * @date 2022年4月21日下午10:57:42
	 */
	public void onRefresh(Rank rank) {
		this.getLeaderboard().put(rank.getUniqueId(), rank);
	}

	/**
	 * 获取排行榜快照
	 * 
	 * @return void
	 * @date 2022年4月21日下午11:02:43
	 */
	public Collection<Rank> getRankInfo() {
		try {
			return this.getLeaderboard().getRankInfo();
		} catch (Exception e) {
			logger.error("getRankInfo error.", e);
		}
		return Collections.emptyList();
	}

	/**
	 * 构建排行榜消息
	 * 
	 * @return 协议对象
	 */
	@Override
	public IProtocol buildProtocol(long playerId) {
		RespRankInfoBuilder builder = RespRankInfoBuilder.newInstance();
		builder.setRankType(rankTypeEnum().getConfigId());
		// 从排行榜里面获取到的排行数据构建成排行榜对象
		// 并且构建出自己的排行信息
		PBRankDto selfDto = null;
		for (Rank rank : this.getRankInfo()) {
			PBRankDto rankDto = buildRankDto(rank);
			if (rankDto == null) {
				continue;
			}
			builder.addRankDtos(rankDto);
			if (selfDto == null && rank.getUniqueId() == playerId) {
				selfDto = rankDto;
			}
		}
		// 如果自己的排行信息为null,则拿到模块数据,构建排行信息
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
		// FIXME 这里从玩家影子对象里面获取到玩家信息, 丢给排行榜
		dtoBuilder.setPlayerProfile(shadow.toProto());
		return dtoBuilder.build();
	}

	/**
	 * 根据玩家的模块信息,构建临时排行对象
	 * 
	 * @param playerId
	 *            玩家id
	 * @return 排行榜对象
	 */
	public abstract PBRankDto buildRankDto(long playerId);

}
