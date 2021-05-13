package com.cat.server.game.module.rank.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.rank.assist.ISorter;
import org.springframework.stereotype.Repository;

/**
* @author Jeremy
*/
@PO(name = "rank")
public class Rank extends RankPo implements IPersistence, ISorter{

	public Rank() {

	}
	
	public Rank(int curServerId, int rankType, int uniqueId, int firstValue) {
		setCurServerId(curServerId);
		setRankType(rankType);
		setUniqueId(uniqueId);
		setFirstValue(firstValue);
	}
	
//	@Override
//	public Class<?> clazz() {
//		return Rank.class;
//	}

	@Override
	public Long getId() {
		return getUniqueId();
	}

	@Override
	public Long getFirstOrder() {
		return getFirstValue();
	}

	@Override
	public Long getSecondOrder() {
		return getSecondValue();
	}

	@Override
	public Long getThirdOrder() {
		return getThirdValue();
	}
	
}
