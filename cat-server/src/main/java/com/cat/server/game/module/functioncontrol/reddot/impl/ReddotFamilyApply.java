package com.cat.server.game.module.functioncontrol.reddot.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cat.server.game.module.family.IFamilyService;
import com.cat.server.game.module.family.assist.FamilyPrivilege;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.functioncontrol.define.ReddotConditionEnum;
import com.cat.server.game.module.functioncontrol.reddot.IFunctionReddot;
import com.cat.server.game.module.group.domain.DefaultApply;

/**
 * 红点条件-家族申请
 * @author Jeremy
 */
@Component
public class ReddotFamilyApply implements IFunctionReddot {
	
	@Autowired private IFamilyService familyService;

	@Override
	public int getCondition() {
		return ReddotConditionEnum.FAMILY_APPLY.getConditionId();
	}

	@Override
	public int checkReddot(long playerId) {
		boolean privilege = familyService.hasPrivilege(playerId, FamilyPrivilege.APPROVE);
		if (!privilege) {
			return 0;
		}
		Family family = familyService.getFamilyByPlayerId(playerId);
		if (family == null) {
			return 0;
		}
		Map<Long, DefaultApply> applys = family.getApplys();
		return applys.size();
	}

}
