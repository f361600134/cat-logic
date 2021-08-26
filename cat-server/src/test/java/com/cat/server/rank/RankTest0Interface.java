package com.cat.server.rank;


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cat.server.game.module.rank.assist.RankDescComparator;
import com.cat.server.game.module.rank.utils.Leaderboard;
import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;

/**
 * 排行榜工具基础功能测试<br>
 * 对所有接口进行测试, 新增接口记得也要测试一遍
 */
public class RankTest0Interface {
	
	private Leaderboard<Pair<Long, Integer>, RankingData> dataList;
	
	//=======排行榜配置数据=======
	//排行榜最大限制
	private int maxnum = 5;
	//测试数据量
	private int testNum = 5;
	
	//=======统计数据=======
	private int updateCount;
	private int deleteCount;
	
	/**
	 * 初始化排行榜
	 */
	@BeforeTest
	public void init() {
		dataList = new Leaderboard<>(new RankDescComparator<>(), maxnum, (updateSet, deleteSet)->{
			if (updateSet.size() > 0) {
				updateCount += updateSet.size();
				//更新数据
				System.out.println("default listen, updateSet.size:"+updateSet.size()+", updateSet:"+updateSet);
			}
			if (deleteSet.size() > 0) {
				deleteCount += deleteSet.size();
	           	//删掉数据
				System.out.println("default listen, deleteSet.size:"+deleteSet.size()+", deleteSet:"+deleteSet);
			}
			System.out.println("updateCount:"+updateCount+", deleteCount:"+deleteCount);
        });
	}
	
	/**
	 * 开始测试
	 * 1. 插入5条数据, 
	 * 2.所有接口测试
	 */
	@Test
	public void test() {
		for (int i = 1; i <= testNum; i++) {
			long refreshTime = TimeUtil.now();
			long id = i;
			int parameter = i;
			long value = i;
			long value2 = i;
			RankingData data = new RankingData(id, parameter, value, value2, refreshTime);
			System.out.println("dataList put data before:"+data);
			dataList.put(data.getKey(), data);
			System.out.println("dataList put data after:"+dataList);
			System.out.println("------------------------------");
		}
	}
	
	/**
	 * 打印统计数据
	 */
	@AfterTest
	public void after() {
		System.out.println("=======排行榜限制数量:"+maxnum+", 测试数据量:"+testNum);
		System.out.println("获取第一名的数据:"+dataList.getFirst());
		System.out.println("获取最后一名的数据:"+dataList.getLast());
		System.out.println("获取指定名次的数据:"+dataList.getByRank(1));
		System.out.println("获取指定数据的名次:"+dataList.getRank(dataList.getFirst()));
		System.out.println("获取所有名次的数据:"+dataList.getRankInfo());
		
		System.out.println("获取指定id的名次::"+dataList.getRankByKey(dataList.getLast().getKey()));
		System.out.println("获取排行榜当前数量:"+dataList.size());
		System.out.println("根据名次截取排行榜,:"+dataList.subRankInfo(1, 3));
		System.out.println("根据指定对象截取排行榜,:"+dataList.subRankInfo(dataList.getByRank(2), dataList.getByRank(4)));
		System.out.println("根据指定对象截取排行榜, 有头无尾,:"+dataList.subRankInfo(dataList.getByRank(2), true, dataList.getByRank(4), false));
		
		dataList.remove(dataList.getLast().getKey());
		System.out.println("移除排行榜最后一名后:"+dataList);
		dataList.clear();
		System.out.println("清空排行榜后:"+dataList.values());
	}
	
	
}
