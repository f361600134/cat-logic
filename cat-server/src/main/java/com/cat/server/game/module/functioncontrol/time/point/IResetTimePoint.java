package com.cat.server.game.module.functioncontrol.time.point;

public interface IResetTimePoint {
	
	/**
	 * 无需重置
	 */
	int RESET_FREE = -1;

	/**
	 * 获取当前时间的重置时间点<br>
	 * @return
	 */
	long getResetTimePoint(long now);
	
}
