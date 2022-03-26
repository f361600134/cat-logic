package com.cat.server.game.module.mission2.handler.type;

import org.springframework.stereotype.Component;

import com.cat.server.game.data.config.local.ConfigMissionMain;
import com.cat.server.game.module.mission.domain.MissionTypeEnum;
import com.cat.server.game.module.mission2.handler.AbstractMissionHandler;
import com.cat.server.game.module.mission2.type.MissionTypeData;

/**
 * 主线任务实现类
 * @author Jeremy
 */
@Component
public class MainMissionHandler extends AbstractMissionHandler<ConfigMissionMain>{

	@Override
	public MissionTypeEnum getMissionType() {
		return MissionTypeEnum.MAIN;
	}

	@Override
	public MissionTypeData getMissionTypeData(long playerId, boolean createIfAbsent) {
		// TODO Auto-generated method stub
		return null;
	}

}
