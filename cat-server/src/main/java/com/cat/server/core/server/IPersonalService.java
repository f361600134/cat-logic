package com.cat.server.core.server;

public interface IPersonalService {
	
	/**
	 * 玩家登录
	 */
	default public void onLogin(long playerId) {
	}
	
	/**
	 * 玩家登出游戏
	 */
	default public void onLogout(long playerId) {
	}
	
}
