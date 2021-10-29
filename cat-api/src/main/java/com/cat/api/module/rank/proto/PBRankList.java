package com.cat.api.module.rank.proto;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 排行榜列表对象
 * @author Jeremy
 */
public class PBRankList {

	/**
	 * 排行榜类型id
	 */
	private int rankType;
	
	/**
	 * 排序类型1:值大则前,2:值小则前
	 */
	private int sorted;

	/**
	 * 限制数量
	 */
	private int limit;

	/**
	 * 排行榜列表
	 */
	private List<PBRank> rankInfos;

	public PBRankList() {
	}

	public int getRankType() {
		return rankType;
	}

	public void setRankType(int rankType) {
		this.rankType = rankType;
	}

	public List<PBRank> getRankInfos() {
		return rankInfos;
	}

	public void setRankInfos(List<PBRank> rankInfos) {
		this.rankInfos = rankInfos;
	}
	
	public void addRankInfos(PBRank rankInfo) {
		this.rankInfos.add(rankInfo);
	}

	public int getSorted() {
		return sorted;
	}

	public void setSorted(int sorted) {
		this.sorted = sorted;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
}
