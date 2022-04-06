package com.cat.server.game.helper.condition.impl;

import com.cat.server.game.helper.condition.ICondition;

/**
 * 副本条件
 * @auth Jeremy
 * @date 2022年4月6日下午9:40:39
 */
public class StageCondition implements ICondition{
	
	/**
	 * 副本配置id
	 */
	private final int stageId;
	
	public StageCondition(int stageId) {
		this.stageId = stageId;
	} 
	
	public int getStageId() {
		return stageId;
	}

	@Override
	public boolean accept(long playerId) {
		//通关副本达到xx章节
		return false;
	}

}
