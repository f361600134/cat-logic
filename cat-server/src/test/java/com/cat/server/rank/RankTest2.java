package com.cat.server.rank;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cat.server.game.module.rank.assist.RankDescComparator;
import com.cat.server.game.module.rank.utils.Leaderboard;
import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;

/**
 * 模拟排行榜批量入榜<br>
 */
public class RankTest2 {
	
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
	 * 初始化排行榜
	 */
	@Before
	public void init() {
		dataList = new Leaderboard<>(new RankDescComparator<RankingData>(), maxnum, (updateSet, deleteSet)->{
			updateCount += updateSet.size();
			deleteCount += deleteSet.size();
//        	//更新数据
//			System.out.println("default listen, updateSet.size:"+updateSet.size()+", updateSet:"+updateSet);
//           	//删掉数据
//			System.out.println("default listen, deleteSet.size:"+deleteSet.size()+", deleteSet:"+deleteSet);
			System.out.println("updateCount:"+updateCount+", deleteCount:"+deleteCount);
        });
	}
	
	/**
	 * 开始测试
	 */
	@Test
	public void test() {
		long startTime = System.currentTimeMillis();
		Map<Pair<Long, Integer>, RankingData> map = new HashMap<>();
		for (int i = 1; i <= testNum; i++) {
//		for (int i = testNum; i >= 1; i--) {
			long refreshTime = TimeUtil.now();
			long id = i;
			int parameter = i;
			long value = i;
			long value2 = i;
			RankingData data = new RankingData(id, parameter, value, value2, refreshTime);
//			System.out.println("dataList put data before:"+data);
			map.put(data.getKey(), data);
//			System.out.println("dataList put data after:"+dataList);
//			System.out.println("------------------------------");
		}
		dataList.putAll(map);
		costTime = (System.currentTimeMillis() - startTime);
	}
	
	/**
	 * 打印统计数据
	 */
	@After
	public void after() {
		System.out.println("=======排行榜限制数量:"+maxnum+", 测试数据量:"+testNum+", 耗时(ms):"+costTime);
		System.out.println("RankTest updateCount:"+updateCount+", deleteCount:"+deleteCount);
		System.out.println("===================================");
		System.out.println("dataList:"+dataList.size());
		System.out.println("dataList:"+dataList.values());
	}
	
	
}
