package com.cat.robot.common.event;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

/**
 * 游戏事件总线
 */
public class GameEventBus {
	public final Logger logger = LoggerFactory.getLogger(GameEventBus.class.getName());

	private static GameEventBus gameEventBus = new GameEventBus();
	private EventBus eventBus = new EventBus();

	private GameEventBus() {
	}

	public static GameEventBus instance() {
		return gameEventBus;
	}

	/**
	 * 注册观察者相关的订阅事件
	 * 
	 * @param object
	 */
	public void register(Object object) {
		eventBus.register(object);
	}

	public void register(Collection<Class<?>> classes) {
		for (Class<?> cls : classes) {
			try {
				Object o = cls.newInstance();
				register(o);
			} catch (Exception e) {
				logger.error("注册观察者过程出现异常", e);
			}
		}
	}

	/**
	 * 发送事件
	 * 
	 * @param event
	 */
	public void post(Object event) {
		eventBus.post(event);
	}

	public void post(Object... events) {
		if (events.length <= 0) {
			return;
		}
		for (Object event : events) {
			if (event == null) {
				continue;
			}
			post(event);
		}
	}
}
