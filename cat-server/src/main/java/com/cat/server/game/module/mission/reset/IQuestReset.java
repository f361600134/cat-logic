package com.cat.server.game.module.mission.reset;

import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.handler.IQuestHandler;

public interface IQuestReset {
	
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
	boolean checkAndReset(long playerId, Quest quest, IQuestHandler<?> questHandler);

}
