package com.cat.server.game.module.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.config.event.RemoteConfigRefreshEvent;
import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.function.event.FunctionCheckReddotEvent;
import com.cat.server.game.module.player.event.PlayerDailyResetEvent;
import com.cat.server.game.module.player.event.PlayerLeaveGameEvent;
import com.cat.server.game.module.player.event.PlayerLevelUpEvent;
import com.cat.server.game.module.player.event.PlayerLoginEvent;
import com.google.common.eventbus.Subscribe;

/**
 * Function事件类
 */
@Component
public class FunctionObserver implements IObserver {

	@Autowired
	private FunctionService service;

	/**
	 * 红点检测事件
	 * @param event FunctionCheckReddotEvent
	 */
	@Subscribe
	public void onFunctionCheckReddotEvent(FunctionCheckReddotEvent event) {
		service.onCheckReddot(event.getPlayerId(), event.getModuleId());
	}

	/**
	 * 每日重置事件
	 * @param event PlayerDailyResetEvent
	 */
	@Subscribe
	public void onPlayerDailyResetEvent(PlayerDailyResetEvent event) {
		service.onPlayerDailyReset(event.getPlayerId(), event.getTime());
	}

	/**
	 * 玩家登录事件
	 * @param event PlayerLoginEvent
	 */
	@Subscribe
	public void onPlayerLoginEvent(PlayerLoginEvent event) {
		service.onLogin(event.getPlayerId(), event.getTime());
	}
	
	/**
	 * 玩家登出事件
	 * @param event PlayerLeaveGameEvent
	 */
	@Subscribe
	public void onPlayerLeaveGameEvent(PlayerLeaveGameEvent event) {
		service.onLogout(event.getPlayerId());
	}
	
	/**
	 * 玩家升级事件
	 * @param event PlayerLevelUpEvent
	 */
	@Subscribe
	public void onPlayerLevelUpEvent(PlayerLevelUpEvent event) {
		service.onCheckFunctionOpen(event.getPlayerId());
	}
	
	/**
	 * 远程配置刷新事件
	 * @param event PlayerLevelUpEvent
	 */
	@Subscribe
	public void onRemoteConfigRefreshEvent(RemoteConfigRefreshEvent event) {
		//TODO 
//		service.onCheckFunctionOpen(event.getPlayerId());
	}

	@Override
	public int order() {
		return IObserver.LOWEST;
	}
}