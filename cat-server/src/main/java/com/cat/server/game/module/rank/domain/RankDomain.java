package com.cat.server.game.module.rank.domain;

import java.util.ArrayList;
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

import com.cat.api.core.task.impl.DefaultTokenTaskQueueExecutor;
import com.cat.api.module.rank.proto.PBRank;
import com.cat.api.module.rank.utils.ILeaderboard;
import com.cat.api.module.rank.utils.Leaderboard;
import com.cat.net.network.base.IProtocol;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IModuleDomain;
import com.cat.server.game.data.config.local.ConfigRank;
import com.cat.server.game.data.proto.PBRank.PBRankDto;
import com.cat.server.game.module.rank.type.IRankType;
import com.google.common.collect.Sets;

import ch.qos.logback.core.joran.conditional.IfAction;


/**
 * 公共数据, key排行榜类型, value作为排行榜
 * 所有的任务通过common线程去处理
* @author Jeremy
* FIXME 所有的阻塞响应,改为异步回调的方式处理, 或者通过锁的方式去处理, 最好的解决方式应该还是acotr(akka),reactor的方式实现
* FIXME 20211020 尝试第一次修改线程模型, 在现在基础的线程模型增加异步回调, 尝试失败, 原因:
* 加了回调之后的, 没有办法在同一个线程内去执行相同模块的任务
*/
public class RankDomain implements IModuleDomain<Integer, Rank>, ILeaderboard<Long, Rank>, IRankType{

	private static final Logger log = LoggerFactory.getLogger(RankDomain.class);
	
	public final static long WAIT_TIME = TimeUnit.SECONDS.toMillis(1);

	/**
	 * 排行榜id,类型
	 */
	private Integer id;

	/**
	 * 当前排行榜数据
	 */
	private final ILeaderboard<Long, Rank> leaderboard;
	
	/**
	 * 公共的线程池处理器
	 */
	private final DefaultTokenTaskQueueExecutor defaultExecutor;
	
	/**
	 * 需要修改的数据, 定时修改
	 * 修改的对象集合
	 */
	private final Set<Rank> updateMap;
	/**
	 * 删除的排行对象集合
	 */
	private final Set<Rank> deleteMap;
	
	/**
	 * 排行榜类型实现类
	 */
	private final IRankType rankType;
	
	/**
	 * 构造方法, 一个domain就是一个排行榜
	 * 根据config获取到排行榜配置
	 * @param id 配置id
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
		RankTypeEnum rankType = RankTypeEnum.getRankType(id);
		if (rankType == null) {
			throw new NullPointerException("rankType error, configId:"+id);
		}
		//	初始化排行榜
		this.leaderboard = new Leaderboard<>(rankType.getComparator(), config.getLimit(), (updates, deletes)->{
			this.updateMap.addAll(updates);
			this.deleteMap.addAll(deletes);
		});
		//初始化排行类型
		this.rankType = rankType.newRankType();
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
		try {
			return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public Integer getRank(Rank v) throws Exception {
		Future<Integer> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getRank(v);
		});
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public Rank getFirst() throws Exception{
		Future<Rank> future = defaultExecutor.submit(0, leaderboard::getFirst);
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public Rank getLast() throws Exception{
		Future<Rank> future = defaultExecutor.submit(0, leaderboard::getLast);
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public Rank getByRank(int rank) throws Exception{
		Future<Rank> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getByRank(rank);
		});
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public Collection<Rank> subRankInfo(int fromRank, int toRank) throws Exception{
		Future<Collection<Rank>> future = defaultExecutor.submit(0, ()->{
			return leaderboard.subRankInfo(fromRank, toRank);
		});
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public Collection<Rank> subRankInfo(Rank from, Rank to) throws Exception{
		Future<Collection<Rank>> future = defaultExecutor.submit(0, ()->{
			return leaderboard.subRankInfo(from, to);
		});
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public Collection<Rank> getRankInfo() throws Exception{
		Future<Collection<Rank>> future = defaultExecutor.submit(0, leaderboard::getRankInfo);
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	////////////业务代码////////////////////
	/**
	 * 存储排行榜数据,这个定时存储?
	 */
	public void onSave() {
		defaultExecutor.submit(0, ()->{
			Iterator<Rank> iter = updateMap.iterator();
			while(iter.hasNext()) {
				Rank rank = iter.next();
				if(rank == null) {
					continue;
				}
				log.info("rank replace:{}", rank);
				rank.save();
				iter.remove();
			}
			//有数据变动的,修改或添加入库
			 iter = deleteMap.iterator();
			while(iter.hasNext()) {
				Rank rank = iter.next();
				log.info("rank remove:{}", rank);
				rank.delete();
				iter.remove();
			}
		});
	}
	
	/**
	 * 排行榜数据转内部的pb列表对象
	 * @return
	 */
	public List<PBRank> toInnerProto(){
		List<PBRank> pbRanks = new ArrayList<>();
		try {
			for (Rank rank: getRankInfo()) {
				pbRanks.add(rank.toInnerProto());
			}
		} catch (Exception e) {
			log.error("toInnerProto error, e:", e);
		}
		return pbRanks;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Integer getRankByKey(Long key) throws Exception{
		Future<Integer> future = defaultExecutor.submit(0, ()->{
			return leaderboard.getRankByKey(key);
		});
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return leaderboard.toString();
	}

	@Override
	public Collection<Rank> subRankInfo(Rank from, boolean fromInclusive, Rank to, boolean toInclusive) throws Exception{
		Future<Collection<Rank>> future = defaultExecutor.submit(0, ()->{
			return leaderboard.subRankInfo(from, fromInclusive, to, toInclusive);
		});
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public Collection<Rank> values() throws Exception{
		Future<Collection<Rank>> future = defaultExecutor.submit(0, leaderboard::values);
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public void clear() throws Exception{
		defaultExecutor.submit(0, leaderboard::clear);
	}

	@Override
	public int size() throws Exception{
		Future<Integer> future = defaultExecutor.submit(0, leaderboard::size);
		return future.get(WAIT_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public PBRankDto buildRankDto(Rank rank) {
		return rankType.buildRankDto(rank);
	}

	@Override
	public IProtocol buildProtocol(Collection<Rank> rankList, long playerId) {
		return rankType.buildProtocol(rankList, playerId);
	}

}

