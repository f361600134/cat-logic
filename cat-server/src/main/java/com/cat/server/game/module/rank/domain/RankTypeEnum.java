package com.cat.server.game.module.rank.domain;

import java.util.Comparator;

import com.cat.server.game.module.rank.assist.RankAscComparator;
import com.cat.server.game.module.rank.type.IRankType;
import com.cat.server.game.module.rank.type.impl.PowerRankType;

public enum RankTypeEnum {
	
	/**战力排行*/
	POWER(1),
	;
	
	//排行榜配置id
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
		return new RankAscComparator<Rank>();
	}
	
	/**
	 * 根据不同的类型构建不同的排行榜实现类
	 * @return
	 */
	public IRankType newRankType() {
		return new PowerRankType(getConfigId());
	}
	
	public static RankTypeEnum getRankType(int configId) {
		for (RankTypeEnum type : values()) {
			if (type.getConfigId() == configId) {
				return type;
			}
		}
		return null;
	}
}
