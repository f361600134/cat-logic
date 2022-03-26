package com.cat.server.game.module.mission2;

import com.cat.server.game.module.mission2.type.MissionTypeData;

public interface IMissionService {
	
	public MissionTypeData getMissionTypeData(long playerId, boolean createIfAbsent);
	
}
