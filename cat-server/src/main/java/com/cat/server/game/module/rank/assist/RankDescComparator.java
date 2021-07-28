package com.cat.server.game.module.rank.assist;

import java.util.Comparator;

/**
 * @author Jeremy Feng
 * 通用排序比较器, 降序排列. 值越大, 排在前面
 */
public class RankDescComparator<T extends ISorter> implements Comparator<T>{
	
	@Override
	public int compare(ISorter o1, ISorter o2) {
		int code = Long.compare(o1.getFirstOrder(), o2.getFirstOrder());
		if (code != 0) return -code;
		
		//排名相同,时间早排名靠前
		code = Long.compare(o1.getSecondOrder(), o2.getSecondOrder());
		if (code != 0) return -code;
		
		code = Long.compare(o1.getThirdOrder(), o2.getThirdOrder());
		if (code != 0) return -code;
		
		//保证不返回0
		return code = -Long.compare(o1.getId(), o2.getId());
	}
	
} 
