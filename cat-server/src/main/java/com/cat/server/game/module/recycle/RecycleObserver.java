package com.cat.server.game.module.recycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.activity.event.ActivityStatusUpdateEvent;
import com.cat.server.game.module.activity.status.IActivityStatus;
import com.cat.server.game.module.resource.event.ResourceUpdateEvent;
import com.google.common.eventbus.Subscribe;

/**
 * Recycle事件类
 */
@Component
public class RecycleObserver implements IObserver{
	
	private static final Logger log = LoggerFactory.getLogger(RecycleObserver.class);

	@Autowired
    private RecycleService service;
	
	/**
	 * 这里监听的是活动结束, 活动结束后所有清掉所有在线玩家的某个活动数据, 所以所有在线玩家的处理, 要丢进玩家线程池处理
	 * @param event
	 */
	@Subscribe
    public void onActivityStatusUpdateEvent(ActivityStatusUpdateEvent event){
		if (event.getStatus() != IActivityStatus.CLOSE) {
			return;
		}
		service.onActivityClose(event.getActivityTypeId());
    }
	
	/**
	 * 资源数量变化
	 */
	@Subscribe
	public void onResourceUpdateEvent(ResourceUpdateEvent event) {
		service.onResourceAddEvent(event.getPlayerId(), event.getItemId(), event.getConfigId(), event.getCurCount());
	}
	
}