package com.cat.server.game.module.functioncontrol.time.point;

/**
 * 不更新时间点
 * @author Jeremy
 */
public class NotResetTimePoint implements IResetTimePoint{
	
	@Override
	public long getResetTimePoint(long now) {
		//如果没有找到相关配置, 表示不需要重置, 返回一个负数
		return RESET_FREE;
	}
	
}
