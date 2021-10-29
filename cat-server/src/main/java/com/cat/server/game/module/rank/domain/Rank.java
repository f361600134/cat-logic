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
				getCurServerId(),
				getUniqueId(),
				getFirstValue(),
				getSecondValue(),
				getThirdValue(),
				getCreateTime()
		);
	}
}
