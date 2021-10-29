package com.cat.api.module.rank.assist;

import java.util.Comparator;

/**
 * 排行榜升序比较器, 值越小, 排在越前
 * @author Jeremy
 * @date 2019年4月2日下午9:07:51
 */
public class RankAscComparator<T extends ISorter> implements Comparator<T>{

	/**
	 * 排序器类型
	 */
	public static final int SORTED = 1;

	/**
	 * 禁止外部实例化
	 */
	RankAscComparator(){}

	/**
	 * 升序排序
	 * o1<o2 : 返回1
	 * o1>o2 : 返回-1
	 * o1=o2 : 返回0
	 */
	@Override
	public int compare(ISorter o1, ISorter o2) {
		//	先比较一级排序值
		int code = Long.compare(o1.getFirstOrder(), o2.getFirstOrder());
		if (code != 0) return code;
		
		code = Long.compare(o1.getSecondOrder(), o2.getSecondOrder());
		if (code != 0) return code;
		
		//排名相同,时间早排名靠前
		code = Long.compare(o1.getThirdOrder(), o2.getThirdOrder());
		if (code != 0) return code;

		//排名相同,时间早排名靠前
		code = Long.compare(o1.getFourthOrder(), o2.getFourthOrder());
		if (code != 0) return code;
		
		//保证不返回0
		return code = Long.compare(o1.getId(), o2.getId());
	}
}
