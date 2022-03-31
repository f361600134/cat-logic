package com.cat.server.game.data.config.local;

import com.alibaba.fastjson.annotation.JSONField;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigFunctionBase;
import com.cat.server.game.module.functioncontrol.time.parse.ResetTimePointParser;
import com.cat.server.game.module.functioncontrol.time.point.IResetTimePoint;


/**
 * gn.功能控制.xlsx<br>
 * function.json<br>
 * @author auto gen
 */
@ConfigPath("function.json")
public class ConfigFunction extends ConfigFunctionBase {
	
	
	@JSONField(name="reset", deserializeUsing = ResetTimePointParser.class)
	private IResetTimePoint resetTimePoint;

	public IResetTimePoint getResetTimePoint() {
		return resetTimePoint;
	}

	public void setResetTimePoint(IResetTimePoint resetTimePoint) {
		this.resetTimePoint = resetTimePoint;
	}

}
