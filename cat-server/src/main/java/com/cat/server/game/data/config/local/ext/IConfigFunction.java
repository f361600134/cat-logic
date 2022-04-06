package com.cat.server.game.data.config.local.ext;

import com.cat.server.game.helper.condition.ICondition;

public interface IConfigFunction {
	
	/**
	 * 获取条件判断
	 */
	public ICondition getCondition();

}
