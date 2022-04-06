package com.cat.server.game.helper.condition;

import java.util.List;

/**
 * 条件与, 所有条件成立, 返回true
 * @auth Jeremy
 * @date 2022年4月6日下午9:38:16
 */
public class AndCondition extends AbstractCondition {

	public AndCondition() {
	}
	
	public AndCondition(List<ICondition> conditions) {
		super(conditions);
	}

	@Override
	public boolean accept(long playerId) {
		if (this.conditions.isEmpty()) {
			return false;
		}
		for (final ICondition condition : conditions) {
			if (!condition.accept(playerId)) {
				return false;
			}
		}
		return true;
	}
	
//	public static AndCondition and(ICondition ...conditions) {
//		AndCondition andCondition = new AndCondition();
//		for (ICondition condition : conditions) {
//			andCondition.addCondition(condition);
//		}
//		return andCondition;
//	}
	
}
