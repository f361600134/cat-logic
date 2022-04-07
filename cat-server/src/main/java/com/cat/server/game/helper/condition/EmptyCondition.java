package com.cat.server.game.helper.condition;

/**
 * 空条件, 没有配置条件, 默认解锁
 * @auth Jeremy
 * @date 2022年4月6日下午9:39:26
 */
public class EmptyCondition implements ICondition {

	@Override
	public boolean accept(long playerId) {
		return true;
	}
	
}
