package com.cat.server.game.module.functioncontrol.reddot;

/**
 * 功能红点
 * @author Jeremy
 */
public interface IFunctionReddot {
	
	/**
	 * 获取条件, 一个条件对应一个红点 
	 * @return
	 */
	int getCondition();
	
	/**
	 * 检测红点<br>
	 * 根据条件去实现对应的红点检测, 通常一个条件对应一个红点条件, 没办法复用<br>
	 * 之所以独立出来, 是为了跟模块隔离与解耦
	 * @return
	 */
	public int checkReddot(long playerId);

}
