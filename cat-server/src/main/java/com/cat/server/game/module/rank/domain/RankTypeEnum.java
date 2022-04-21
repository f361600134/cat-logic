package com.cat.server.game.module.rank.domain;

import com.cat.api.module.rank.assist.RankAscComparator;
import com.cat.api.module.rank.assist.RankComparators;
import com.cat.api.module.rank.assist.RankDescComparator;
import com.cat.server.game.module.rank.type.IRankType;
import com.cat.server.game.module.rank.type.impl.PlayerOtherRankType;
import com.cat.server.game.module.rank.type.impl.PlayerPowerRankType;

import java.util.Comparator;

public enum RankTypeEnum {
	
	/**战力排行*/
	POWER(1){
		public IRankType newRankType() {
			return new PlayerPowerRankType();
		}
	},
	
	/**研习社*/
	LearnCommunityRank(2){
		public IRankType newRankType() {
			return new PlayerOtherRankType();
		}
	},
	
	/**其他排行*/
	OTHER(3){
		public IRankType newRankType() {
			return new PlayerOtherRankType();
		}
	},
	;

	/**
	 * 排行榜配置id
	 */
	private int configId;
	
	private RankTypeEnum(int configId) {
		this.configId = configId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}
	
	public Comparator<Rank> getComparator(){
		return RankComparators.descComparator();
	}

	/**
	 * 排序类型
	 * @return
	 */
	public int getSorted(){
		int sorted = 0;
		if (getComparator() instanceof RankAscComparator){
			sorted = RankAscComparator.SORTED;
		}else if(getComparator() instanceof RankDescComparator){
			sorted = RankDescComparator.SORTED;
		}
		return sorted;
	}
	
	/**
	 * 根据不同的类型构建不同的排行榜实现类
	 * @return
	 */
	public abstract IRankType newRankType();
	
	public static RankTypeEnum getRankType(int configId) {
		for (RankTypeEnum type : values()) {
			if (type.getConfigId() == configId) {
				return type;
			}
		}
		return null;
	}
}
