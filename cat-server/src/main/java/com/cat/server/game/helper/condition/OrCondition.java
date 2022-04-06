package com.cat.server.game.helper.condition;

import java.util.List;

/**
 * 条件或,任意条件成立, 结果成立
 * @auth Jeremy
 * @date 2022年4月6日下午9:38:40
 */
public class OrCondition extends AbstractCondition {

	public OrCondition() {
		super();
	}
	
	public OrCondition(List<ICondition> conditions) {
		super(conditions);
	}

	@Override
	public boolean accept(long playerId) {
		for (final ICondition condition : conditions) {
			if (condition.accept(playerId)) {
				return true;
			}
		}
		return false;
	}
	
}
