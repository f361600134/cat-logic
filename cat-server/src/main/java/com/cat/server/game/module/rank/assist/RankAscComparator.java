package com.cat.server.game.module.rank.assist;

import java.util.Comparator;

import com.cat.server.game.module.rank.domain.Rank;

/**
 * 排行榜升序比较器, 值越小, 排在越前
 * @auth Jeremy
 * @date 2019年4月2日下午9:07:51
 */
public class RankAscComparator implements Comparator<Rank>{

	/**
	 * 升序排序
	 * o1<o2 : 返回1
	 * o1>o2 : 返回-1
	 * o1=o2 : 返回0
	 */
	@Override
	public int compare(Rank o1, Rank o2) {
		//	先比较一级排序值
		int code = Long.compare(o1.getFirstOrder(), o2.getFirstOrder());
		if (code != 0) return code;
		
		code = Long.compare(o1.getSecondOrder(), o2.getSecondOrder());
		if (code != 0) return code;
		
		//排名相同,时间早排名靠前
		code = Long.compare(o1.getThirdOrder(), o2.getThirdOrder());
		if (code != 0) return code;
		
		//保证不返回0
		return code = Long.compare(o1.getId(), o2.getId());
	}
}
