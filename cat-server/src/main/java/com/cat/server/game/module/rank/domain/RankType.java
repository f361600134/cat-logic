package com.cat.server.game.module.rank.domain;

import java.util.Comparator;

import com.cat.server.game.module.rank.assist.RankAscComparator;

public enum RankType {
	
	DEFAULT(1),
	;
	
	//排行榜配置id
	private int configId;
	
	private RankType(int configId) {
		this.configId = configId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}
	
	public Comparator<Rank> getComparator(){
		return new RankAscComparator();
	}
	
	public static RankType getRankType(int configId) {
		for (RankType type : values()) {
			if (type.getConfigId() == configId) {
				return type;
			}
		}
		return null;
	}
}
