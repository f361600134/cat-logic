package com.cat.rank.service.module.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.cat.api.module.rank.assist.ISorter;
import com.cat.api.module.rank.proto.PBRank;

/**
* @author Jeremy
*/
public class Rank implements ISorter {

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

	public Rank() {

	}

	public Rank(int curServerId, int rankType, long uniqueId, long firstValue) {
		this.curServerId = curServerId;
		this.rankType = rankType;
		this.uniqueId = uniqueId;
		this.firstValue = firstValue;
	}

	public Rank(int curServerId, int rankType, long uniqueId, long firstValue, long secondValue, long thirdValue, long createTime) {
		this.curServerId = curServerId;
		this.rankType = rankType;
		this.uniqueId = uniqueId;
		this.firstValue = firstValue;
		this.secondValue = secondValue;
		this.thirdValue = thirdValue;
		this.createTime = createTime;
	}

	/**
	 * 静态构造, 创建一个排行榜对象
	 * @param curServerId 服务器id
	 * @param rankType 排行榜类型
	 * @param uniqueId 唯一id
	 * @param firstValue 第一比较值
	 * @param secondValue 第二比较值
	 * @param thirdValue 第三比较值
	 * @return 排行榜对象
	 */
	public static Rank create(int curServerId, int rankType, long uniqueId, long firstValue, long secondValue, long thirdValue, long createTime){
		return new Rank(curServerId, rankType, uniqueId, firstValue, secondValue, thirdValue, createTime);
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

	@Override
	public long getId() {
		return getUniqueId();
	}

	@Override
	public long getFirstOrder() {
		return getFirstValue();
	}

	@Override
	public long getSecondOrder() {
		return getSecondValue();
	}

	@Override
	public long getThirdOrder() {
		return getThirdValue();
	}

	@Override
	public long getFourthOrder() {
		return getCreateTime();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
	
	/**
	 * 重写hashcode,只要服务器id,排行榜类型,以及唯一id一致, 表示是同一个对象
	 */
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + curServerId;
		result = prime * result + rankType;
		result = prime * result + (int)(uniqueId ^ uniqueId>>>32);
        return result;
    }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Rank)) {
			return false;
		}
		Rank rank = (Rank) obj;
		if (curServerId == rank.getCurServerId() 
				&& rankType == rank.getRankType()
				&& uniqueId == rank.getRankType()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 转协议对象
	 * @return 协议对象
	 */
	public PBRank toInnerProto() {
		return PBRank.create(curServerId, rankType, uniqueId, firstValue, secondValue, thirdValue, createTime);
	}
	
}
