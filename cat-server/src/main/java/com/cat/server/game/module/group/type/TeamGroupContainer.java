package com.cat.server.game.module.group.type;

import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.group.AbstractGroupContainer;
import com.cat.server.game.module.team.domain.Team;

/**
 * 队伍容器
 * @author Jeremy
 */
public class TeamGroupContainer extends AbstractGroupContainer<Team> {
    @Override
    public Team newGroup(long groupId, String groupName) {
        return Team.create(groupId, groupName);
    }
}
