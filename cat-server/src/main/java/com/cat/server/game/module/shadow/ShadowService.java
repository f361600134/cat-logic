package com.cat.server.game.module.shadow;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.shadow.domain.Shadow;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


/**
 * Shadow控制器
 * @author Jeremy
 */
@Service
class ShadowService implements IShadowService, ILifecycle {
	
	private static final Logger log = LoggerFactory.getLogger(ShadowService.class);
	
	@Autowired private IDataProcess process;
	
	/**
	 * 最大缓存2000
	 */
	public final static int MAXNUM = 2 << 10;
	/**
	 * 初始化32
	 */
	public final static int INITNUM = 2 << 4;
	/**
	 * 默认初始化512
	 */
	public final static int LOADNUM = 2 << 8;
	
	
	/**
	 * 缓存的影子数据
	 */
	private Cache<Long, Shadow> cache = CacheBuilder.newBuilder()
			.expireAfterAccess(60, TimeUnit.MINUTES)// 在给定时间内没有被读/写访问,则清除
			.maximumSize(MAXNUM)// 最大容量
			.initialCapacity(INITNUM)// 初始容量
			.removalListener(notification->{
				Shadow shadow = (Shadow)notification.getValue();
				shadow.update();
			})
			.build();
	
	/**
	 * 当初始化<br>
	 * 默认初始化玩家缓存起来
	 */
	public void load() {
		String sql = "select * from shadow order by updateTime desc limit "+LOADNUM;
		List<Shadow> list = process.selectBySql(Shadow.class, sql);
		list.forEach(shadow->{
			shadow.afterLoad();
			cache.put(shadow.getPlayerId(), shadow);
		});
	}
	
	/**
	 * 这里依赖cache->remove回调<br>
	 * 清掉所有数据, 被清掉的数据会调用到回调方法, 最终得以存储
	 */
	public void save() {
		cache.invalidateAll();
	}
	
	/**
	 * 从数据库加载指定玩家id的数据
	 * @return
	 */
	public Shadow loadOne(long playerId) {
		Shadow shadow = process.selectByPrimaryKey(Shadow.class, new Object[] {playerId});
		shadow.afterLoad();
		return shadow;
	}

	
	/////////////接口方法////////////////////////
	
	@Override
	public Shadow get(long playerId) {
		Shadow shadow = cache.getIfPresent(playerId);
		if (shadow == null) {
			shadow = loadOne(playerId);
		}
		return shadow;
	}

	@Override
	public Shadow getOrCreate(long playerId) {
		Shadow shadow = get(playerId);
		if (shadow == null) {
			shadow = Shadow.create(playerId);
		}
		return shadow;
	}
	
	@Override
	public void onPlayerUpdate(Player player) {
		Shadow shadow = get(player.getPlayerId());
		shadow.replacement(player);
	}
	
	@Override
	public void start() throws Throwable {
		load();
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}
	
	@Override
	public void stop() throws Throwable {
		save();
	}

}
 
 
