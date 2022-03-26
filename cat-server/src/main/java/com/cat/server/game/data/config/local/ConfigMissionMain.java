package com.cat.server.game.data.config.local;

import java.util.Map;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigMissionMainBase;
import com.cat.server.game.data.config.local.ext.IConfigMission;


/**
 * rw.任务-主线任务.xlsx<br>
 * mission_main.json<br>
 * @author auto gen
 */
@ConfigPath("mission_main.json")
public class ConfigMissionMain extends ConfigMissionMainBase implements IConfigMission{

	@Override
	public Map<Integer, Integer> getMissionReward() {
		return this.getReward();
	}

	@Override
	public boolean autoSubmit() {
		return this.getAutoSubmit() == 1;
	}

}
