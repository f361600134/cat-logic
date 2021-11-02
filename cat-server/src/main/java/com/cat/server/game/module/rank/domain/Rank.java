package com.cat.server.game.module.rank.domain;

import com.cat.api.module.rank.assist.ISorter;
import com.cat.api.module.rank.proto.PBRank;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;

/**
* @author Jeremy
*/
@PO(name = "rank")
public class Rank extends RankPo implements IPersistence, ISorter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8651597101407917069L;

	public Rank() {

	}
	
	public Rank(int curServerId, int rankType, long uniqueId, long firstValue) {
		setCurServerId(curServerId);
		setRankType(rankType);
		setUniqueId(uniqueId);
		setFirstValue(firstValue);
	}
	
	public Rank(int curServerId, int rankType, long uniqueId, long firstValue, long secondValue, long thirdValue, long createTime) {
		setCurServerId(curServerId);
		setRankType(rankType);
		setUniqueId(uniqueId);
		setFirstValue(firstValue);
		setSecondValue(secondValue);
		setThirdValue(thirdValue);
		setCreateTime(createTime);
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
	
	/**
	 * 对象转内部的proto对象
	 * @return
	 */
	public PBRank toInnerProto() {
		return PBRank.create(
				getCurServerId(),
				getRankType(),
				getUniqueId(),
				getFirstValue(),
				getSecondValue(),
				getThirdValue(),
				getCreateTime()
		);
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
		result = prime * result + (int)(firstValue ^ firstValue>>>32);
		result = prime * result + (int)(secondValue ^ secondValue>>>32);
		result = prime * result + (int)(thirdValue ^ thirdValue>>>32);
		result = prime * result + (int)(createTime ^ createTime>>>32);
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
				&& uniqueId == rank.getUniqueId()
				&& firstValue == rank.getFirstValue()
				&& secondValue == rank.getSecondValue()
				&& thirdValue == rank.getThirdValue()
				&& createTime == rank.getCreateTime()) {
			return true;
		}
		return false;
	}
	
}
