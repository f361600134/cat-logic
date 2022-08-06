package com.cat.server.game.module.functioncontrol.reddot.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.game.module.functioncontrol.define.ReddotConditionEnum;
import com.cat.server.game.module.functioncontrol.reddot.IFunctionReddot;
import com.cat.server.game.module.mission.IMissionService;

/**
 * 红点条件
 * @author Jeremy
 */
@Component
public class MainMissionReddot implements IFunctionReddot {
	
	@Autowired private IMissionService missionService;

	@Override
	public int getCondition() {
		return ReddotConditionEnum.MAIN_MISSION.getConditionId();
	}

	@Override
	public int checkReddot(long playerId) {
		return missionService.checkReddot(playerId);
	}

}
