package com.cat.server.rank;

import com.cat.api.module.rank.assist.RankComparators;
import com.cat.api.module.rank.utils.Leaderboard;
import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模拟排行榜批量入榜<br>
 */
public class RankTest2PutAll {
	
	private Leaderboard<Pair<Long, Integer>, RankingData> dataList;
	
	//=======排行榜配置数据=======
	//排行榜最大限制
	private int maxnum = 1000;
	//测试数据量
	private int testNum = 100000;
	
	//=======统计数据=======
	private int updateCount;
	private int deleteCount;
	
	//耗时统计
	private long costTime;
	
	/**
	 * 数据为升序的
	 */
	Map<Pair<Long, Integer>, RankingData> ascListMap = new LinkedHashMap<>();
	
	/**
	 * 初始化排行榜
	 */
	@BeforeTest
	public void init() {
		dataList = new Leaderboard<>(RankComparators.descComparator(), maxnum, (updateSet, deleteSet)->{
			updateCount += updateSet.size();
			deleteCount += deleteSet.size();
        });
		for (int i = 1; i <= testNum; i++) {
//		for (int i = testNum; i >= 1; i--) {
				long refreshTime = TimeUtil.now();
				long id = i;
				int parameter = i;
				long value = i;
				long value2 = i;
				RankingData data = new RankingData(id, parameter, value, value2, refreshTime);
				ascListMap.put(data.getKey(), data);
			}
	}
	
	/**
	 * 单线程下的putall
	 */
	@Test
	public void testSingleThread() {
		long startTime = System.currentTimeMillis();
		dataList.putAll(ascListMap);
		costTime = (System.currentTimeMillis() - startTime);
	}
	
	/**
	 * 多线程下的putAll<br>
	 * 用于测试出现的异常情况, 会出现以下并发安全问题<br>
	 * java.lang.NullPointerException at java.util.TreeMap.rotateLeft(Unknown Source)
	 * java.lang.NullPointerException at java.util.TreeMap.fixAfterInsertion(Unknown Source)
	 */
	@Test(threadPoolSize=5, invocationCount=3)
	public void test() {
		long startTime = System.currentTimeMillis();
		dataList.putAll(ascListMap);
		costTime = (System.currentTimeMillis() - startTime);
	}
	
	/**
	 * 打印统计数据
	 */
	@AfterTest
	public void after() {
		System.out.println("=======排行榜限制数量:"+maxnum+", 测试数据量:"+testNum+", 耗时:"+costTime);
		System.out.println("RankTest updateCount:"+updateCount+", deleteCount:"+deleteCount);
		System.out.println("===================================");
		System.out.println("dataList:"+dataList.size());
//		System.out.println("dataList:"+dataList.values());
	}
	
	
}
