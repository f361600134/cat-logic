package com.cat.server.game.module.recycle.strategy;

/**
 * 回收策略
 * @author Jeremy
 */
public interface IRecycleStrategy {
	
	/**
	 * 计算时间戳节点
	 * @return
	 */
	public long calculateTimePoint(long time);

}
