package com.cat.server.game.module.recycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.net.core.executor.DisruptorStrategy;
import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.activity.event.ActivityStatusUpdateEvent;
import com.cat.server.game.module.activity.status.IActivityStatus;
import com.cat.server.game.module.hero.event.HeroAddEvent;
import com.cat.server.game.module.item.event.ItemAddEvent;
import com.cat.server.game.module.player.IPlayerService;
import com.google.common.eventbus.Subscribe;

/**
 * Recycle事件类
 */
@Component
public class RecycleObserver implements IObserver{
	
	private static final Logger log = LoggerFactory.getLogger(RecycleObserver.class);

	@Autowired
    private RecycleService service;
	
	@Autowired
    private IPlayerService playerService;
	
	/**
	 * 这里监听的是活动结束, 活动结束后所有清掉所有在线玩家的某个活动数据, 所以所有在线玩家的处理, 要丢进玩家线程池处理
	 * @param event
	 */
	@Subscribe
    public void onActivityStatusUpdateEvent(ActivityStatusUpdateEvent event){
		if (event.getStatus() != IActivityStatus.CLOSE) {
			return;
		}
		//丟到玩家玩家线程池
		for (Long playerId : playerService.getOnlinePlayerIds()) {
			DisruptorStrategy.get(DisruptorStrategy.SINGLE).execute(playerService.getSessionId(playerId),()->{
				try {
					service.onActivityClose(playerId, event.getActivityTypeId());
				} catch (Exception e) {
					log.error("DisruptorDispatchTask error", e);
				}
			});
		}
    }
	
	/**
	 * 当监听到新增道具
	 */
	@Subscribe
	public void onItemAddEvent(ItemAddEvent event) {
		service.onResourceAddEvent(event.getPlayerId(), event.getItemId(), event.getConfigId());
	}
	
	/**
	 * 当监听到新增武将
	 */
	@Subscribe
	public void onHeroAddEvent(HeroAddEvent event) {
		service.onResourceAddEvent(event.getPlayerId(), event.getHeroId(), event.getConfigId());
	}
	
}