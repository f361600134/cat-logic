package com.cat.server.game.module.function.reddot.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.game.module.family.IFamilyService;
import com.cat.server.game.module.family.assist.FamilyPrivilege;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.function.define.ReddotTypeEnum;
import com.cat.server.game.module.function.reddot.IFunctionReddot;
import com.cat.server.game.module.group.domain.DefaultApply;

/**
 * 红点条件-家族申请
 * 
 * @author Jeremy
 */
@Component
public class ReddotFamilyApply implements IFunctionReddot {

	@Autowired
	private IFamilyService familyService;

	@Override
	public int getReddotId() {
		return ReddotTypeEnum.FAMILY_APPLY.getReddotId();
	}

	@Override
	public List<Integer> checkReddot(long playerId) {
		boolean privilege = familyService.hasPrivilege(playerId, FamilyPrivilege.APPROVE);
		if (!privilege) {
			return Collections.emptyList();
		}
		Family family = familyService.getFamilyByPlayerId(playerId);
		if (family == null) {
			return Collections.emptyList();
		}
		Map<Long, DefaultApply> applys = family.getApplys();
		return Collections.singletonList(applys.size());
	}

}
