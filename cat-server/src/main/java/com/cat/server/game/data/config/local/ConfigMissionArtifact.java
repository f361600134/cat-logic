package com.cat.server.game.data.config.local;

import java.util.Map;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigMissionArtifactBase;
import com.cat.server.game.data.config.local.ext.IConfigMission;


/**
 * rw.任务-神器.xlsx<br>
 * mission_artifact.json<br>
 * @author auto gen
 */
@ConfigPath("mission_artifact.json")
public class ConfigMissionArtifact extends ConfigMissionArtifactBase implements IConfigMission{

	@Override
	public Map<Integer, Integer> getMissionReward() {
		return this.getMissionReward();
	}

	@Override
	public boolean autoSubmit() {
		return this.getAutoSubmit() == 1;
	}

}
