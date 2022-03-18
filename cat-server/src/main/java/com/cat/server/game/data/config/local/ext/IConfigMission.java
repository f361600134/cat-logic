package com.cat.server.game.data.config.local.ext;

import java.util.Map;

import com.cat.server.core.config.container.IGameConfig;

public interface IConfigMission extends IGameConfig{
	
	/**
	 * 完成条件类型
	 * @return
	 */
	public int[] getCompleteType();
	
	/**
	 * 完成条件
	 * @return
	 */
	public int[] getCompleteCondition();
	
	/**
	 * 完成值
	 * @return
	 */
	public int[] getCompleteValue();
	
	/**
	 * 奖励
	 * @return
	 */
	public Map<Integer, Integer> getMissionReward();

}
