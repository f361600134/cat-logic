package com.cat.server.game.data.config.local;

import com.alibaba.fastjson.annotation.JSONField;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigTestBase;
import com.cat.server.game.module.activity.time.parse.TimePointParser;
import com.cat.server.game.module.activity.time.point.ITimePoint;

@ConfigPath("Test.json")
public class ConfigTest extends ConfigTestBase{
	
	@JSONField(name="timePoint", deserializeUsing = TimePointParser.class)
	private ITimePoint time;

	public ITimePoint getTime() {
		return time;
	}

	public void setTime(ITimePoint time) {
		this.time = time;
	}

}
