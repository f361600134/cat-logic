package com.cat.server.rank;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cat.server.game.module.rank.assist.RankDescComparator;
import com.cat.server.game.module.rank.utils.Leaderboard;
import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;

/**
 * 模拟排行榜逐条数据入榜模拟测试<br>
 * 测试结果：排行榜限制1000, 测试数据量100000, 且每次入榜都成功,  
 * 
 */
public class RankTest1 {
	
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
	List<RankingData> ascList = new ArrayList<>();
	/**
	 * 初始化排行榜
	 */
	@Before
	public void init() {
		dataList = new Leaderboard<>(new RankDescComparator<RankingData>(), maxnum, (updateSet, deleteSet)->{
			if (updateSet.size() > 0) {
				updateCount += updateSet.size();
				//更新数据
//				System.out.println("default listen, updateSet.size:"+updateSet.size()+", updateSet:"+updateSet);
			}
			if (deleteSet.size() > 0) {
				deleteCount += deleteSet.size();
	           	//删掉数据
//				System.out.println("default listen, deleteSet.size:"+deleteSet.size()+", deleteSet:"+deleteSet);
			}
//			System.out.println("updateCount:"+updateCount+", deleteCount:"+deleteCount);
        });
//		for (int i = 1; i <= testNum; i++) {
			for (int i = testNum; i >= 1; i--) {
				long refreshTime = TimeUtil.now();
				long id = i;
				int parameter = i;
				long value = i;
				long value2 = i;
				RankingData data = new RankingData(id, parameter, value, value2, refreshTime+i);
				ascList.add(data);
		}
		System.out.println(ascList.get(0));
		System.out.println(ascList.get(1));
		System.out.println(ascList.get(2));
	}
	
	/**
	 * 开始测试
	 */
	@Test
	public void test() {
		long startTime = System.currentTimeMillis();
		for (RankingData data : ascList) {
			dataList.put(data.getKey(), data);
		}
		costTime = (System.currentTimeMillis() - startTime);
	}
	
	/**
	 * 打印统计数据
	 */
	@After
	public void after() {
		System.out.println("排行榜限制数量:"+maxnum+", 测试数据量:"+testNum+", 耗时(ms):"+costTime);
		System.out.println("RankTest updateCount:"+updateCount+", deleteCount:"+deleteCount);
//		System.out.println("===================================");
//		System.out.println("dataList:"+dataList);
	}
	
	
}
