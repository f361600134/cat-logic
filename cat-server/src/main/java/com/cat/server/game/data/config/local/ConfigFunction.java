package com.cat.server.game.data.config.local;

import com.alibaba.fastjson.annotation.JSONField;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigFunctionBase;
import com.cat.server.game.data.config.local.ext.IConfigFunction;
import com.cat.server.game.helper.condition.ICondition;
import com.cat.server.game.helper.condition.parse.ConditionParse;
import com.cat.server.game.module.functioncontrol.time.parse.ResetTimePointParser;
import com.cat.server.game.module.functioncontrol.time.point.IResetTimePoint;


/**
 * gn.功能控制.xlsx<br>
 * function.json<br>
 * @author auto gen
 */
@ConfigPath("function.json")
public class ConfigFunction extends ConfigFunctionBase implements IConfigFunction{
	
	
	@JSONField(name="reset", deserializeUsing = ResetTimePointParser.class)
	private IResetTimePoint resetTimePoint;
	
	@JSONField(name="unock", deserializeUsing = ConditionParse.class)
	private ICondition condition;

	public IResetTimePoint getResetTimePoint() {
		return resetTimePoint;
	}

	public void setResetTimePoint(IResetTimePoint resetTimePoint) {
		this.resetTimePoint = resetTimePoint;
	}

	@Override
	public ICondition getCondition() {
		return this.condition;
	}

}
