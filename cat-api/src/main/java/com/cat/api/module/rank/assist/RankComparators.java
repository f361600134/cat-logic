package com.cat.api.module.rank.assist;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装一套默认比较器集合, 比较器不提供对外实例化方法. 避免使用者频繁生成比较器
 * @author Jeremy
 */
public class RankComparators {

    /**
     * 实例化一个比较器对象,避免重复实例化对象
     */
    private static final RankDescComparator<ISorter> DESC_COMPARATOR = new RankDescComparator<>();

    /**
     * 实例化一个比较器对象,避免重复实例化对象
     */
    private static final RankAscComparator<ISorter> ASC_COMPARATOR = new RankAscComparator<>();

    /**
     * 默认比较器集合<br>
     *     key: 比较器类型
     *     value: 比较器
     */
    private static final Map<Integer, Comparator<ISorter>> ComparatorMap = new HashMap<>();
    static {
        ComparatorMap.put(RankDescComparator.SORTED, DESC_COMPARATOR);
        ComparatorMap.put(RankAscComparator.SORTED, ASC_COMPARATOR);
    }
    
	@SuppressWarnings("unchecked")
	public static final <T> T descComparator() {
        return (T)DESC_COMPARATOR;
    }

	@SuppressWarnings("unchecked")
	public static final <T> T ascComparator() {
        return (T)ASC_COMPARATOR;
    }
	
	@SuppressWarnings("unchecked")
	public static final <T> T getComparator(int sorted) {
        return (T)ComparatorMap.get(sorted);
    }
	
}
