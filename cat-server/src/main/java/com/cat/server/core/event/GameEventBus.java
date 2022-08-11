package com.cat.server.core.event;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.module.function.event.FunctionCheckReddotEvent;
import com.cat.server.game.module.player.IPlayerService;
import com.google.common.eventbus.EventBus;

/**
 * 游戏事件总线
 *支持两种事件机制
 *发布订阅,默认注册. 不能移除掉
 *监听机制,像活动类任务, 只有活动开启时才注册, 活动结束取消注册.
 *
 *这里可能会有一个问题, 因为IObserver被spring包装过,所以要确定一下发送的时间是否可以被识别
 */
public class GameEventBus implements ILifecycle{
	
	private static final Logger logger = LoggerFactory.getLogger(GameEventBus.class);
	
	@Autowired(required = false)
	private List<IObserver> observers;
	
	private EventBus eventBus;
	
	/**
	 * 是否运行状态, 如果不是运行状态, 则不能把事件发送出去
	 */
	private volatile boolean running;
	
	/**
	 * 伪单例, 获取事件处理器
	 */
	private static GameEventBus gameEventBus;
	
	/**
	 * 初始化构造
	 */
	public GameEventBus(){
		this.eventBus = new EventBus(); 
	}
	
	/**
	 * 注册观察者相关的订阅事件
	 * @param
	 */
	public void register() {
//		observers.stream().sorted((o1, o2)-> {
//			return Integer.compare(o1.order(), o2.order());
//		});
		Collections.sort(observers, (o1, o2)->{
			return Integer.compare(o1.order(), o2.order());
		});
		observers.forEach(o -> { 
			if(o.isRegister()) {
				eventBus.register(o);
			}
		});
	}
	
	/**
	 * 发送事件
	 * @param event
	 */
	public void post(IEvent event) {
		if (!this.running) {
			return;
		}
		eventBus.post(event);
	}
	
	/**
	 * 发送多个事件
	 * @param events
	 */
	public void post(IEvent ...events) {
		if (!this.running) {
			return;
		}
		if (events.length <= 0) {
			return;
		}
		for (IEvent event : events) {
			if (event == null) {
				continue;
			}
			post(event);
		}
	}
	
	/**
	 * 发送玩家事件
	 * @param event
	 */
	public void post(long playerId, PlayerBaseEvent event) {
		if (!this.running) {
			return;
		}
		event.setPlayerId(playerId);
		eventBus.post(event);
	}
	
	/**
	 * 群发玩家事件<br>
	 * 所有玩家共用一个事件
	 * @param event
	 */
	public void post(Collection<Long> playerIds, PlayerBaseEvent event) {
		if (!this.running) {
			return;
		}
		playerIds.forEach(playerId->{
			event.setPlayerId(playerId);
			eventBus.post(event);
		});
	}
	
	/**
	 * 群发玩家事件<br>
	 * 所有玩家共用一个事件
	 * @param event
	 */
	public void postOnlinePlayers(PlayerBaseEvent event) {
		if (!this.running) {
			return;
		}
		//FIXME 这里是为数不多的地方, 框架层依赖了业务层代码
		//实际上可以把事件丢到业务层,当成一个服务去做
		IPlayerService playerService = SpringContextHolder.getBean(IPlayerService.class);
		Collection<Long> playerIds = playerService.getOnlinePlayerIds();
		playerIds.forEach(playerId->{
			event.setPlayerId(playerId);
			eventBus.post(event);
		});
	}
	
	public static GameEventBus getInstance() {
        return  SpringContextHolder.getBean(GameEventBus.class);
    }
	
	/**
	 * 	发布事件
	 * @param event
	 */
	public static void publishEvent(IEvent event) {
		if (gameEventBus == null) {
			gameEventBus =  SpringContextHolder.getBean(GameEventBus.class);
		}
		gameEventBus.post(event);
	}
	
	@Override
	public void start() throws Throwable {
		this.running = true;
		this.register();
	}
	
	@Override
	public void stop() throws Exception {
		this.running = false;
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC_INITIALIZATION.getPriority();
	}
}
