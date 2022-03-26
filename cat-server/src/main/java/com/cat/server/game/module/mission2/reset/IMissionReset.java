package com.cat.server.game.module.mission2.reset;

import com.cat.server.game.module.mission2.handler.IMissionHandler;
import com.cat.server.game.module.mission2.type.Mission;

public interface IMissionReset {
	
	/*** 不重置重置*/
	public final static int NOT_RESET = 0;
	
	/*** 每日重置*/
	public final static int DAILY_RESET = 1;
	
	/*** 每周重置*/
	public final static int WEEKLY_RESET = 2;
	
	/**
	 * 获取重置类型
	 * @return
	 */
	int getResetType();
	
	/**
	 * 检车并重置
	 */
	boolean checkAndReset(long playerId, Mission mission, IMissionHandler<?> missionHandler);

}
