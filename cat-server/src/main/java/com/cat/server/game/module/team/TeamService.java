package com.cat.server.game.module.team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.api.core.task.TokenTaskQueueExecutor;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.group.IMember;
import com.cat.server.game.module.group.domain.DefaultApply;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.team.assist.TeamConstant;
import com.cat.server.game.module.team.assist.TeamPosition;
import com.cat.server.game.module.team.domain.Team;
import com.cat.server.game.module.team.domain.TeamDomain;
import com.cat.server.utils.TimeUtil;

/**
 * TeamService 队伍service处理队伍玩法的业务逻辑
 * 组队逻辑公共处理
 * 考虑优化点:
 * 队伍信息第一次下发给客户端, 新成员的加入, 旧成员的退出是否需要单独推送给客户端由客户端处理? 需要
 *
 */
@Service
class TeamService implements ITeamService{

    /**	公共的线程池处理器*/
    @Autowired
    private TokenTaskQueueExecutor defaultExecutor;
    
    @Autowired private IPlayerService playerService;


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
     * @return 查询到的队伍列表
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
            //	加入申请队列, 通知队长, 有人发起请求, 队长审批请求, 成功后把玩家加入队伍
//            FamilyApply familyApply = FamilyApply.create(playerId);
//            family.getApplys().put(familyApply.getPlayerId(), familyApply);
            DefaultApply apply = DefaultApply.create(playerId);
            team.getApplys().put(apply.getPlayerId(), apply);
            //TODO 红点通知队长
            
            return ErrorCode.SUCCESS;
        });
        return future.get();
    }
    
    /**
     * 审批进入队伍
     * @param playerId 操作开人的玩家id
     * @param applyPlayerId 申请的玩家id
     * @type type 审批类型, 0拒绝, 1同意, 2忽略
     */
    public ErrorCode approval(long playerId, long applyPlayerId, int type) throws Exception{
        Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
            Team team = domain.getGroupByPlayerId(playerId);
            if (team == null){
                return ErrorCode.TEAM_NOT_EXIST;
            }
            // 不是队长则视为失败
            if (playerId != team.getLeaderId()){
                return ErrorCode.TEAM_NOT_LEADER;
            }
            DefaultApply apply = team.getApplys().get(applyPlayerId);
            if (apply == null) {
            	return ErrorCode.TEAM_APPLY_EXPIRE;
			}
            if (TimeUtil.now() > apply.getCreateTime() + TeamConstant.LIMIT_TIME) {
				return ErrorCode.TEAM_APPLY_EXPIRE;
			}
            if (type == 0) {
            	//拒绝,通知申请玩家被拒绝
            	//TODO 通知申请玩家已经被拒绝
			}else if (type == 1) {
				//同意,通知玩家进入队伍成功,推送最新的队伍信息
				IMember member = team.newMember(playerId);
				domain.enter(member, team.getId());
				//TODO 1.2更新队伍其他成员信息至新加入成员
                //TODO 1.2.更新新加入成员至队伍其他成员
                //TODO 2. 更新队伍所有人的信息之所有成员
				//更新最新的队伍信息给所有成员
				this.responseInfoToMembers(team);
			}else if (type == 2) {//忽略,不做任何处理
				//DO nothing...
			}
            team.getApplys().remove(applyPlayerId);
            return ErrorCode.SUCCESS;
        });
        return future.get();
    }
    
    /**
     * 审批同意
     * @date 2021年5月15日下午11:34:27
     */
    private void approvalAgree() {
    	
    }

    @Override
    public long getPlayerTeamId(long playerId) {
        return domain.getGroupIdByPlayerId(playerId);
    }

    /**
     * 根据队伍id获取到队伍
     * @param teamId 队伍id
     * @return 队伍对象
     */
    @Override
    public Long getTeamIdByTeamId(long teamId){
        Team team = getTeamByTeamId(teamId);
        if (team == null){
            return 0L;
        }
        return team.getId();
    }
    
    /**
     * 根据队伍id获取到队伍
     * @param teamId 队伍id
     * @return 队伍对象
     */
    @Override
    public Team getTeamByTeamId(long teamId){
        return domain.get(teamId);
    }

    @Override
    public Collection<Long> getMemberIdsByTeamId(long teamId) {
        Team team =  getTeamByTeamId(teamId);
        if (team == null){
            return Collections.emptyList();
        }
        return new ArrayList<>(team.getMembers().keySet());
    }

    /**
     * 根据玩家id获取到队伍
     * @param playerId 玩家id
     * @return 队伍对象
     */
    public Team getTeamByPlayerId(long playerId){
        return domain.getGroupByPlayerId(playerId);
    }
    
    /**
     * 更新队伍信息至所有成员
     * @date 2021年5月15日下午11:42:43
     */
    public void responseInfoToMembers(Team team) {
    	if (team == null) {
			return;
		}
    	//TODO 组装队伍信息
    	//TeamInfo teamInfo = TeamInfo.create();
//    	for (long playerId : team.getMembers().keySet()) {
//    		playerService.sendMessage(playerId, teamInfo);
//		}
    }
}
