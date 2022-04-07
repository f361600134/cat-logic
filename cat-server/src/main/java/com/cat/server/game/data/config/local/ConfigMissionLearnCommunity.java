package com.cat.server.game.data.config.local;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigMissionLearnCommunityBase;
import com.cat.server.game.data.config.local.ext.IConfigActivityMission;


/**
 * rw.任务-研习社.xlsx<br>
 * mission_learn_community.json<br>
 * @author auto gen
 */
@ConfigPath("mission_learn_community.json")
public class ConfigMissionLearnCommunity extends ConfigMissionLearnCommunityBase implements IConfigActivityMission{

	@Override
	public boolean autoSubmit() {
		return this.getAutoSubmit() == 1;
	}

}
