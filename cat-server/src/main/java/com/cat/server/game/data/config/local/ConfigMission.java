package com.cat.server.game.data.config.local;

import com.alibaba.fastjson.annotation.JSONField;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigMissionBase;
import com.cat.server.game.module.activity.time.parse.TimePointParser;
import com.cat.server.game.module.activity.time.point.ITimePoint;

@ConfigPath("ConfigHeroInfo.json")
public class ConfigMission extends ConfigMissionBase{
	
	@JSONField(name="startTime", deserializeUsing = TimePointParser.class)
	private ITimePoint time;

	public ITimePoint getTime() {
		return time;
	}

	public void setTime(ITimePoint time) {
		this.time = time;
	}

}
