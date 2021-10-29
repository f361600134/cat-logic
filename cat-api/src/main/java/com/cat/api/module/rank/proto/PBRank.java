package com.cat.api.module.rank.proto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 排行榜协议对象
* @author Jeremy
*/
public class PBRank {

	/** 当前服务器Id*/
	private int curServerId;
	/** 排行榜类型*/
	private int rankType;
	/** 对象id*/
	private long uniqueId;
	/** 第一比较值,最主要的比较值*/
	private long firstValue;
	/** 第二比较值,第一比较值相同的情况下, 比较此值*/
	private long secondValue;
	/** 第三比较值,第二比较值相同的情况下, 比较此值*/
	private long thirdValue;
	/** 入榜时间*/
	private long createTime;

	public PBRank() {

	}

	public PBRank(int curServerId, int rankType, int uniqueId, int firstValue) {
		this.curServerId = curServerId;
		this.rankType = rankType;
		this.uniqueId = uniqueId;
		this.firstValue = firstValue;
	}

	public PBRank(int curServerId, int rankType, long uniqueId, long firstValue, long secondValue, long thirdValue, long createTime) {
		this.curServerId = curServerId;
		this.rankType = rankType;
		this.uniqueId = uniqueId;
		this.firstValue = firstValue;
		this.secondValue = secondValue;
		this.thirdValue = thirdValue;
		this.createTime = createTime;
	}

	public int getCurServerId() {
		return curServerId;
	}

	public int getRankType() {
		return rankType;
	}

	public long getUniqueId() {
		return uniqueId;
	}

	public long getFirstValue() {
		return firstValue;
	}

	public long getSecondValue() {
		return secondValue;
	}

	public long getThirdValue() {
		return thirdValue;
	}

	public long getCreateTime() {
		return createTime;
	}
	
	public static PBRank create(int curServerId, int rankType, long uniqueId, long firstValue, long secondValue, long thirdValue, long createTime) {
		return new PBRank(curServerId, rankType, uniqueId, firstValue, secondValue, thirdValue, createTime);
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}

}
