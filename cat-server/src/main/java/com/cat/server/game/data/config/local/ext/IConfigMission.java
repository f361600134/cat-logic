package com.cat.server.game.data.config.local.ext;

import java.util.Map;

import com.cat.server.core.config.container.IGameConfig;

public interface IConfigMission extends IGameConfig{
	
	/**
	 * 完成条件类型
	 * @return 支持多完成类型
	 */
	public int[] getCompleteType();
	
	/**
	 * 完成条件
	 * @return 支持多条件
	 */
	public int[] getCompleteCondition();
	
	/**
	 * 完成值
	 * @return 支持多完成值
	 */
	public int[] getCompleteValue();
	
	/**
	 * 奖励
	 * @return 奖励Map
	 */
	public Map<Integer, Integer> getMissionReward();
	
	/**
	 * 是否自动提交<br>
	 * true: 是, false: 否
	 */
	public boolean autoSubmit();
	
	/**
	 * 重置类型<br>
	 * 0: 不重置<br>
	 * 1: 每日重置<br>
	 * 2: 每周重置
	 */
	public int resetType();

}
