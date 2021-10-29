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

import java.util.HashMap;
import java.util.Map;
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
 *
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
//@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
public class RankTestJmh1 {
	
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
	
	@Setup
    public void prepare() {
		//准备排行榜
		this.dataList = new Leaderboard<>(RankComparators.descComparator(), maxnum, (updateSet, deleteSet)->{
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
                .include(RankTestJmh1.class.getSimpleName())
                //结果导出文件
                .result("result.json")
                //结果格式化
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
	}
	
	
}
