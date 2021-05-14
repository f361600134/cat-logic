package com.cat.server.game.module.team;

import com.cat.server.core.task.TokenTaskQueueExecutor;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.family.domain.FamilyApply;
import com.cat.server.game.module.family.domain.FamilyDomain;
import com.cat.server.game.module.group.IMember;
import com.cat.server.game.module.team.assist.TeamPosition;
import com.cat.server.game.module.team.domain.Team;
import com.cat.server.game.module.team.domain.TeamDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;

/**
 * 组队玩法service
 *
 */
@Service
class TeamService implements ITeamService{

    /**	公共的线程池处理器*/
    @Autowired
    private TokenTaskQueueExecutor defaultExecutor;


    /**
     * 组队domain域
     */
    private TeamDomain domain = new TeamDomain();

    /**
     * 创建队伍
     */
    public ErrorCode createTeam(long playerId, String name) throws Exception{
        Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
            if (domain.containsName(name)){
                return ErrorCode.TEAM_NAME_EXIST;
            }
            if(domain.getGroupIdByPlayerId(playerId) > 0){
                return ErrorCode.TEAM_ALREADY_IN_TEAM;
            }
            domain.create(playerId, name);
            return ErrorCode.SUCCESS;
        });
        return future.get();
    }

    /**
     * 离开队伍
     * 如果是队长离开队伍, 队伍第二个玩家成为队长, 如果没有第二个玩家, 则解散该队伍
     */
    public ErrorCode exitTeam(long playerId) throws Exception{
        Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
            Team team = domain.getGroupByPlayerId(playerId);
            if (team == null){
                return ErrorCode.TEAM_NOT_EXIST;
            }
            //记录玩家队长标志
            boolean isLeader = playerId == team.getLeaderId();
            //退出队伍
            domain.exit(playerId, team.getId());
            if (isLeader){
                if (team.getMembers().size() > 0){
                    //退出队伍成功后,如果还有玩家,设置下一个玩家为队长
                    IMember nextMember = team.getMembers().values().iterator().next();
                    nextMember.setPosition(TeamPosition.LEADER.getValue());
                }else{
                    //退出队伍成功后,如果没有玩家,则解散队伍
                    domain.destroy(team);
                }
            }
            return ErrorCode.SUCCESS;
        });
        return future.get();
    }

    /**
     * 开除玩家
     * @param playerId 操作开人的玩家id
     * @param firePlayerId 被踢出队伍的玩家id
     */
    public ErrorCode fire(long playerId, long firePlayerId) throws Exception{
        Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
            Team team = domain.getGroupByPlayerId(playerId);
            if (team == null){
                return ErrorCode.TEAM_NOT_EXIST;
            }
            // 操作对象是自己视为失败
            if (playerId == firePlayerId){
                return ErrorCode.TEAM_FIRE_SELF;
            }
            //	被开的玩家是否存在
            if (team.getPosition(firePlayerId) <= 0) {
                return ErrorCode.TEAM_MEMBER_NOT_EXIST;
            }
            domain.exit(firePlayerId, team.getId());
            return ErrorCode.SUCCESS;
        });
        return future.get();
    }

    /**
     * 解散队伍
     * @param playerId 操作解散队伍的玩家id
     * @return 错误码
     */
    public ErrorCode destroy(long playerId) throws Exception{
        Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
            Team team = domain.getGroupByPlayerId(playerId);
            if (team == null){
                return ErrorCode.TEAM_NOT_EXIST;
            }
            // 不是队长则视为失败
            if (playerId != team.getLeaderId()){
                return ErrorCode.TEAM_NOT_LEADER;
            }
            domain.destroy(team);
            return ErrorCode.SUCCESS;
        });
        return future.get();
    }

    /**
     * 查找队伍
     * @param keyword 关键字
     * @return
     */
    public Collection<Team> searchTeam(String keyword){
        return domain.searchGroup(keyword);
    }

    /**
     * 申请进入队伍
     * @param playerId 申请玩家id
     * @return familyId 申请家族id
     */
    public ErrorCode applyJoinTeam(long playerId, long teamId) throws Exception{
        Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
            final Team team = domain.get(teamId);
            if (team == null) {
                return ErrorCode.TEAM_NOT_EXIST;
            }
            //TODO	判断是否符合进入队伍的条件
            //	通知队长, 有人发起请求
//            FamilyApply familyApply = FamilyApply.create(playerId);
//            family.getApplys().put(familyApply.getPlayerId(), familyApply);
            return ErrorCode.SUCCESS;
        });
        return future.get();
    }

    /**
     * 根据队伍id获取到队伍
     * @param teamId 队伍id
     * @return 队伍对象
     */
    public Team getTeamByTeamId(long teamId){
        return domain.get(teamId);
    }

    /**
     * 根据玩家id获取到队伍
     * @param playerId 玩家id
     * @return 队伍对象
     */
    public Team getTeamByPlayerId(long playerId){
        return domain.getGroupByPlayerId(playerId);
    }
}
