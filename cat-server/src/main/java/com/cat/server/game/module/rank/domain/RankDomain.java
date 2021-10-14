package com.cat.server.game.module.rank.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IModuleDomain;
import com.cat.server.core.task.impl.DefaultTokenTaskQueueExecutor;
import com.cat.server.game.data.config.local.base.ConfigRank;
import com.cat.server.game.module.rank.utils.ILeaderboard;
import com.cat.server.game.module.rank.utils.Leaderboard;
import com.google.common.collect.Sets;


/**
 * 公共数据, key排行榜类型, value作为排行榜
 * 所有的任务通过common线程去处理
* @author Jeremy
* FIXME 所有的阻塞响应,改为异步回调的方式处理, 或者通过锁的方式去处理
*/
public class RankDomain implements IModuleDomain<Integer, Rank>, ILeaderboard<Long, Rank>{

	private static final Logger log = LoggerFactory.getLogger(RankDomain.class);
	
	public final static long WAIT_TIME = TimeUnit.SECONDS.toMillis(3);
	
	//排行榜id,类型?
	private Integer id;
	/**
	 * 当前排行榜数据
	 */
	private ILeaderboard<Long, Rank> leaderboard;
	
	/**
	 * 公共的线程池处理器
	 */
	private DefaultTokenTaskQueueExecutor defaultExecutor;
	
	/**
	 * 需要修改的数据, 定时修改
	 */
	private Set<Rank> updateMap; //要修改的集合?
	private Set<Rank> deleteMap; //要删除的集合?
	
	/**
	 * 构造方法, 一个domain就是一个排行榜
	 * 根据config获取到排行榜配置
	 * @param configId
	 */
	public RankDomain(int id){
		this.id = id;
		this.updateMap = Sets.newHashSet(); 
		this.deleteMap = Sets.newHashSet();
		//	初始化时, 从spring上下文获取一次defaultExecutor
		this.defaultExecutor =  SpringContextHolder.getBean(DefaultTokenTaskQueueExecutor.class);
		ConfigRank config = ConfigManager.getInstance().getConfig(ConfigRank.class, id);
		if (config == null) {
			throw new NullPointerException("load rank error, configId:"+id);
		}
		RankType rankType = RankType.getRankType(id);
		if (rankType == null) {
			throw new NullPointerException("rankType error, configId:"+id);
		}
		//	初始化排行榜
		this.leaderboard = new Leaderboard<>(rankType.getComparator(), config.getLimit(), (updates, deletes)->{
			this.updateMap.addAll(updates);
			this.deleteMap.addAll(deletes);
		});
	}
	
	@Override
	public Class<Rank> getBasePoClazz() {
		return Rank.class;
	}

	/**
	 * 懒加载,如果多条线程竞争访问, 这里丢进线程池, 有数据则不初始化, 无数据初始化
	 */
	@Override
	public void initData(Integer id, List<Rank> ranks) {
		this.id = id;
		defaultExecutor.submit(0, ()->{
			if (ranks == null || ranks.isEmpty()) {
				return;
			}
			if (leaderboard.getFirst() != null) {
				return;
			}
			//	List转Map
			Map<Long, Rank> map = new HashMap<>();
			ranks.forEach((rank)->{
				map.put(rank.getId(), rank);
			});
			//	初始化排行榜数据
			//initData(map);
			leaderboard.putAll(map);
		});
	}

	/**
	 * 如果无数据, 忽略, 排行榜无数据可初
	 */
	@Override
	public void initData(Integer id) {
		this.id = id;
	}
	
	@Override
	public Rank put(Long k, Rank v) {
		Future<Rank> future = defaultExecutor.submit(0, ()->{
			return leaderboard.put(k, v);
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

	@Override
	public void putAll(Map<? extends Long, ? extends Rank> m) {
		defaultExecutor.submit(0, ()->{
			leaderboard.putAll(m);
		});
	}

	@Override
	public void remove(Long key) {
		defaultExecutor.submit(0, ()->{
			leaderboard.remove(key);
		});
	}

	@Override
	public Integer getRank(Rank v) {
		Future<Integer> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getRank(v);
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

	@Override
	public Rank getFirst() {
		Future<Rank> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getFirst();
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

	@Override
	public Rank getLast(){
		Future<Rank> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getLast();
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

	@Override
	public Rank getByRank(int rank) {
		Future<Rank> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getByRank(rank);
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

	@Override
	public Collection<Rank> subRankInfo(int fromRank, int toRank){
		Future<Collection<Rank>> future = defaultExecutor.submit(0, ()->{
			return leaderboard.subRankInfo(fromRank, toRank);
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

	@Override
	public Collection<Rank> subRankInfo(Rank from, Rank to){
		Future<Collection<Rank>> future = defaultExecutor.submit(0, ()->{
			return leaderboard.subRankInfo(from, to);
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

	@Override
	public Collection<Rank> getRankInfo(){
		Future<Collection<Rank>> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getRankInfo();
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

	////////////业务代码////////////////////
	/**
	 * 存储排行榜数据,这个定时存储?
	 */
	public void onSave() {
		defaultExecutor.submit(0, ()->{
			//有数据变动的,修改或添加入库
			Iterator<Rank> iter = updateMap.iterator();
			while(iter.hasNext()) {
				Rank rank = iter.next();
				rank.replace();
				iter.remove();
			}
			iter = deleteMap.iterator();
			while(iter.hasNext()) {
				Rank rank = iter.next();
				rank.delete();
				iter.remove();
			}
		});
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Integer getRankByKey(Long key) {
		Future<Integer> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getRankByKey(key);
		});
//		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		return null;
	}

//	@Override
//	public void initData(Map<? extends Long, ? extends Rank> m) {
//		defaultExecutor.submit(0, ()->{
//			leaderboard.initData(m);
//		});
//	}
	
	@Override
	public String toString() {
		return leaderboard.toString();
	}

	@Override
	public Collection<Rank> subRankInfo(Rank from, boolean fromInclusive, Rank to, boolean toInclusive) {
		return leaderboard.subRankInfo(from, fromInclusive, to, toInclusive);
	}

	@Override
	public Collection<Rank> values() {
		return leaderboard.values();
	}

	@Override
	public void clear() {
		leaderboard.clear();
	}

	@Override
	public int size() {
		return leaderboard.size();
	}

}

