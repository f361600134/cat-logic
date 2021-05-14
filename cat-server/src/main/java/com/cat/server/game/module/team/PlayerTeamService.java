package com.cat.server.game.module.team;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.team.domain.Team;
import com.cat.server.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


/**
 * PlayerFamily控制器
 */
@Service
public class PlayerTeamService implements IPlayerTeamService {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerTeamService.class);
	
	@Autowired private IPlayerService playerService;

	@Autowired private TeamService teamService;
	
	/**
	 * 更新信息
	 */
	public void responsePlayerTeamInfo(long playerId, Team team) {
		try {
//			for (PlayerTeam playerTeam : beans) {
//				resp.addArtifactlist(playerTeam.toProto());
//			}
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responsePlayerTeamInfo error, teamId:{}", team.getId());
			log.error("responsePlayerTeamInfo error, e:", e);
		}
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	 * 创建队伍
	 * @param playerId 创建者id
	 * @param name 队伍名称
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode createTeam(long playerId, String name) {
		if (StringUtils.isBlank(name)) {
			return ErrorCode.CONTENT_INVALID_NAME;
		}
		//TODO 敏感字判断,特殊字符,长度判断
		try {
			ErrorCode errorCode = teamService.createTeam(playerId, name);
			if (!errorCode.isSuccess()){
				return errorCode;
			}
			Team team = teamService.getTeamByPlayerId(playerId);
			//	下发队伍信息
			this.responsePlayerTeamInfo(playerId, team);
			//TODO 返回创建队伍成功信息
		} catch (Exception e) {
			e.printStackTrace();
			log.error("createTeam error");
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 查询队伍
	 * @param playerId 查询的玩家id
	 * @param keyword 查询关键字
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode searchTeam(long playerId, String keyword) {
		Collection<Team> teams = teamService.searchTeam(keyword);
		//	TODO 组装队伍信息
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 申请进入队伍
	 * @param playerId 申请的玩家id
	 * @param teamId 队伍id
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode applyJoinTeam(long playerId, long teamId) {
		try {
			teamService.applyJoinTeam(playerId, teamId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("applyJoinTeam error");
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 退出队伍
	 * @param playerId 退出队伍的玩家id
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode exitTeam(long playerId) {
		try {
			ErrorCode errorCode = teamService.exitTeam(playerId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("exitTeam error");
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 查看队伍申请列表
	 * @param playerId
	 * @param name  
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode showTeamApplyInfo(long playerId, String name) {
		return ErrorCode.SUCCESS;
	}
	

	/**
	 * 踢出队伍
	 * @param playerId
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode fire(long playerId, long firePlayerId) {
		try {
			ErrorCode errorCode = teamService.fire(playerId, firePlayerId);
			//	TODO 组装结果消息
		} catch (Exception e) {
			e.printStackTrace();
			log.error("fire error");
		}
		return ErrorCode.SUCCESS;
	}

	/////////////接口方法////////////////////////
	
}