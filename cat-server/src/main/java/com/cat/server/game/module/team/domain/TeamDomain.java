package com.cat.server.game.module.team.domain;

import com.cat.server.game.module.group.AbstractGroupContainer;

/**
 * 队伍域
 * @author Jeremy
 * @date 2021年5月6日下午10:28:48
 */
public class TeamDomain extends AbstractGroupContainer<Team> {

	@Override
	public Team newGroup(long groupId, String groupName) {
		return Team.create(groupId, groupName);
	}


}
