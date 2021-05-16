package com.cat.server.game.module.team.domain;

import com.cat.server.game.module.group.DefaultApply;
import com.cat.server.game.module.group.DefaultMember;
import com.cat.server.game.module.group.IGroup;
import com.cat.server.game.module.group.IMember;
import com.cat.server.game.module.team.assist.TeamPosition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 队伍类型
 * @author Jeremy
 * @date 2021年5月6日下午10:37:12
 */
public class Team implements IGroup {
	
	/**
	 * 队伍id
	 */
	private long teamId;
	
//	/**
//	 * 队长id
//	 */
//	private long leaderId;

	/**
	 * 队伍类型
	 */
	private int type;
	
	/**
	 * 队伍名称
	 */
	private String name;
	
	/**
	 * 队伍条件
	 * 1.等级限制, x级~y级可以加入
	 * 2.职业限制,x职业可以加入
	 * 3.密码限制,输入密码成功后匹配
	 */
	private String condition;

	/**
	 * 成员map
	 */
	private Map<Long, IMember> memberMap = new LinkedHashMap<>();
	
	/**
	 * 申请列表
	 */
	private Map<Long, DefaultApply> applys =  new LinkedHashMap<>();

	public Team(){
	}

	public Team(long teamId){
		this.teamId = teamId;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}



	@Override
	public long getId() {
		return teamId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getPosition(long playerId) {
		IMember member = this.memberMap.get(playerId);
		return member == null ? 0 : member.getPosition();
	}

	@Override
	public Map<Long, IMember> getMembers() {
		return memberMap;
	}

	@Override
	public IMember newLeader(long memberId) {
		DefaultMember member = new DefaultMember();
		member.setMemberId(memberId);
		member.setPosition(TeamPosition.LEADER.getValue());
		return member;
	}

	@Override
	public IMember newMember(long memberId) {
		DefaultMember member = new DefaultMember();
		member.setMemberId(memberId);
		member.setPosition(TeamPosition.NOMAL.getValue());
		return member;
	}
	
	/**
	 * 创建一个队伍
	 * @param id
	 * @param name
	 * @return
	 */
	public static Team create(long id, String name) {
		Team team = new Team(id);
		team.setName(name);
		return team;
	}

	/**
	 * 获取队长id
	 * @return 队长id
	 */
	public long getLeaderId() {
		for (IMember member:memberMap.values()) {
			if (member.getPosition() == TeamPosition.LEADER.getValue()){
				return member.getMemberId();
			}
		}
		return 0L;
	}
	
	/**
	 * 获取申请列表, 超时删除
	 * @return  
	 * @return List<DefaultApply>  
	 * @date 2021年5月15日下午6:03:54
	 */
	public Map<Long, DefaultApply> getApplys() {
		return applys;
	}
	
}
