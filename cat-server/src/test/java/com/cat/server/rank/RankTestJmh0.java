package com.cat.server.rank;

import com.cat.api.module.rank.assist.RankComparators;
import com.cat.api.module.rank.utils.Leaderboard;
import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 排行榜JMH性能测试<br>
 * 基准测试模式申明为：BenchmarkMode(Mode.AverageTime)搭配OutputTimeUnit(TimeUnit.NANOSECONDS)<br>
 * 用于测试每次操作的平均时间, 输出时间单位为纳秒<br>
 * 预热注解:  Warmup, 对代码预热3次, 每次1秒, 单位为秒, 消除JIT对结果的影响.
 * 度量注解: Measurement,表示循环运行5次，总计5秒时间.
 *线程注解: Threads, 提供要运行的默认线程数
 *Fork注解: 表示允许多少个线程并行运行基准测试，如果Fork(1)，那么就是一个线程，这时候就是同步模式。
 *
 *state注解: 
 *通过 State 可以指定一个对象的作用范围，JMH 根据 scope 来进行实例化和共享操作。@State 可以被继承使用，如果父类定义了该注解，子类则无需定义。由于 JMH 允许多线程同时执行测试，不同的选项含义如下：
Scope.Benchmark：所有测试线程共享一个实例，测试有状态实例在多线程共享下的性能
Scope.Group：同一个线程在同一个 group 里共享实例
Scope.Thread：默认的 State，每个测试线程分配一个实例

<h1>背景</h1>
排行榜默认降序排序, 且做了优化, 如果最新入榜数据数据大于最后一名, 才可以入榜.<br>
排行榜容量1000, 测试数据100000万, 预热3次每次1秒, 测试5次每次5秒,  

<h1>测试结果</h1>
两种极端情况测试结果如下, 10万数据平均时间为 3ms~1104ms 左右
在升序数据顺序入榜时, 即: 10万数据统统都入榜成功, 平均时间为1104ms.
在降序顺序顺序入榜时,即: 10万数据只有1000个数据入榜成功, 平均时间为3ms.

Benchmark                 (maxnum)  (testNum)  Mode  Cnt           Score           Error  Units
RankTestJmh0.testPutAsc       1000     100000  avgt    5  1104206228.000 ± 100015487.070  ns/op
RankTestJmh0.testPutDesc      1000     100000  avgt    5     3088348.905 ±     96102.870  ns/op




 *
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
//@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
public class RankTestJmh0 {
	
	//排行榜最大限制
	@Param(value = {"1000"})
	private int maxnum;
	//测试数据量
	@Param(value = {"100000"})
	private int testNum;
	
	//=======统计数据=======
	private long refreshTime = TimeUtil.now();
	
	private int updateCount;
	private int deleteCount;
	
	/** 初始化两个降序排行榜, 一个做升序数据逐个入榜, 一个降序数据逐个入榜 */
	private Leaderboard<Pair<Long, Integer>, RankingData> dataList1;
	private Leaderboard<Pair<Long, Integer>, RankingData> dataList2;
	
	/**
	 * 数据为升序的
	 */
	List<RankingData> ascList = new ArrayList<>();
	
	/**
	 * 数据为降序的descMap
	 */
	List<RankingData> descList = new ArrayList<>();
	
	@Setup
    public void prepare() {
		//准备排行榜
		this.dataList1 = new Leaderboard<>(RankComparators.descComparator(), maxnum, (updateSet, deleteSet)->{
			if (updateSet.size() > 0) {
				updateCount += updateSet.size();
			}
			if (deleteSet.size() > 0) {
				deleteCount += deleteSet.size();
			}
	    });
		this.dataList2 = new Leaderboard<>(RankComparators.descComparator(), maxnum, (updateSet, deleteSet)->{
			if (updateSet.size() > 0) {
				updateCount += updateSet.size();
			}
			if (deleteSet.size() > 0) {
				deleteCount += deleteSet.size();
			}
	    });
		//准备数据
		for (int i = 1; i <= testNum; i++) {
			long id = i;
			int parameter = i;
			long value = i;
			long value2 = i;
			long refreshTime = this.refreshTime + i;
			RankingData data = new RankingData(id, parameter, value, value2, refreshTime);
			ascList.add(data);
		}
		for (int i = testNum; i >= 1; i--) {
			long id = i;
			int parameter = i;
			long value = i;
			long value2 = i;
			long refreshTime = this.refreshTime + i;
			RankingData data = new RankingData(id, parameter, value, value2, refreshTime);
			descList.add(data);
		}
		
    }
	
	/**
	 * 测试input的性能
     * @param blackhole
     */
    @Benchmark
    public Leaderboard<Pair<Long, Integer>, RankingData> testPutAsc(Blackhole blackhole) {
    	for (RankingData data : ascList) {
    		dataList1.put(data.getKey(), data);
		}
    	dataList1.clear();
        blackhole.consume(dataList1);
        return dataList1;
    }
    
    /**
	 * 测试input的性能
     * @param blackhole
     */
    @Benchmark
    public Leaderboard<Pair<Long, Integer>, RankingData> testPutDesc(Blackhole blackhole) {
    	for (RankingData data : descList) {
    		dataList2.put(data.getKey(), data);
		}
    	dataList1.clear();
        blackhole.consume(dataList2);
        return dataList2;
    }
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
        		//加入要测试的类
                .include(RankTestJmh0.class.getSimpleName())
                //结果导出文件
                .result("result.json")
                //结果格式化
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
	}
	
	
}
