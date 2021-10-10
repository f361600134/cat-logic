package com.cat.server.rank;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.cat.server.game.module.rank.assist.RankDescComparator;
import com.cat.server.game.module.rank.utils.Leaderboard;
import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;


/**
 * 排行榜单线程JMH性能测试<br>
 * 基准测试模式申明为：BenchmarkMode(Mode.AverageTime)搭配OutputTimeUnit(TimeUnit.NANOSECONDS)<br>
 * 用于测试每次操作的平均时间, 输出时间单位为纳秒<br>
 * 预热注解:  Warmup, 对代码预热3次, 每次1秒, 单位为秒, 消除JIT对结果的影响.<br>
 * 度量注解: Measurement,表示循环运行5次，总计5秒时间.<br>
 *线程注解: Threads, 提供要运行的默认线程数<br>
 *Fork注解: 表示允许多少个线程并行运行基准测试，如果Fork(1)，那么就是一个线程，这时候就是同步模式。<br>
 *
 *state注解: 
 *通过 State 可以指定一个对象的作用范围，JMH 根据 scope 来进行实例化和共享操作。<br>
State 可以被继承使用，如果父类定义了该注解，子类则无需定义。由于 JMH 允许多线程同时执行测试，不同的选项含义如下：<br>
Scope.Benchmark：所有测试线程共享一个实例，测试有状态实例在多线程共享下的性能<br>
Scope.Group：同一个线程在同一个 group 里共享实例<br>
Scope.Thread：默认的 State，每个测试线程分配一个实例<br>
*
测试结果:
单线程下

<h1>背景</h1>
排行榜默认降序排序, 且做了优化, 如果最新入榜数据数据大于最后一名, 才可以入榜.<br>
排行榜容量1000, 测试数据100000万, 预热3次每次1秒, 测试5次每次5秒,  

<h1>测试结果</h1>
在升序数据顺序入榜时, 平均时间为1104ms.
在降序顺序顺序入榜时,平均时间为3ms.
所以 在无序的
Benchmark                 (maxnum)  (testNum)  Mode  Cnt           Score           Error  Units
RankTestJmh0.testPutAsc       1000     100000  avgt    5  1104206228.000 ± 100015487.070  ns/op
RankTestJmh0.testPutDesc      1000     100000  avgt    5     3088348.905 ±     96102.870  ns/op

<br>
<br>

这里只针对put,putall方法进行了测试, 其他接口使用原生的BiMap,TreeSet方法, 所以没有针对性测试,
 *
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
//@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
public class RankTestJmh0TPS {
	
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
	
	private Leaderboard<Pair<Long, Integer>, RankingData> dataList;
	
	Map<Pair<Long, Integer>, RankingData> map = new HashMap<>();
	
	/**
	 * 排行榜实例, 以及数据准备, 避免这部分影响测试结果
	 */
	@Setup
    public void prepare() {
		//准备排行榜
		this.dataList = new Leaderboard<>(new RankDescComparator<>(), maxnum, (updateSet, deleteSet)->{
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
			map.put(data.getKey(), data);
		}
    }
	
	/**
	 * 测试input的性能
     * @param blackhole
     */
    @Benchmark
    public void testPut(Blackhole blackhole) {
    	for (RankingData data : map.values()) {
    		dataList.put(data.getKey(), data);
		}
        blackhole.consume(dataList);
    }
    
    /**
	 * 测试input的性能
     * @param blackhole
     */
    @Benchmark
    public void testPutAll(Blackhole blackhole) {
    	dataList.putAll(map);
        blackhole.consume(dataList);
    }
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
        		//加入要测试的类
                .include(RankTestJmh0TPS.class.getSimpleName())
                //结果导出文件
                .result("result.json")
                //结果格式化
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
	}
	
	
}
