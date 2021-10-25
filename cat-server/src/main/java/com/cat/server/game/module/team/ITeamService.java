package com.cat.server.game.module.team;


import com.cat.server.game.module.team.domain.Team;

import java.util.Collection;

/**
 * 队伍对外接口
 * @author Jeremy
 */
public interface ITeamService {

    /**
     * 根据玩家id获取到队伍id
     * @param playerId 玩家id
     * @return 队伍id
     */
    public long getPlayerTeamId(long playerId);

    /**
     * 根据队伍id获取到队伍
     * @param teamId 队伍id
     * @return 队伍对象
     */
    public Long getTeamIdByTeamId(long teamId);

    /**
     * 根据队伍id获取到队伍
     * @param teamId 队伍id
     * @return 队伍对象
     */
    public Team getTeamByTeamId(long teamId);

    /**
     * 根据队伍id获取到成员id列表
     * @param teamId 队伍id
     * @return 成员id列表
     */
    public Collection<Long> getMemberIdsByTeamId(long teamId);

}
