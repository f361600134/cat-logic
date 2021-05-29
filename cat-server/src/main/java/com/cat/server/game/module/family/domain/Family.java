package com.cat.server.game.module.family.domain;


import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.orm.util.StateUtils;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.family.assist.FamilyPosition;
import com.cat.server.game.module.group.domain.DefaultApply;
import com.cat.server.game.module.group.domain.DefaultMember;
import com.cat.server.game.module.group.IGroup;
import com.cat.server.game.module.group.IMember;
import com.cat.server.utils.TimeUtil;

import java.util.HashMap;
import java.util.Map;

/**
* @author Jeremy
*/
@PO(name = "family")
public class Family extends FamilyPo implements IPersistence, IGroup{
	
	@Column(PROP_APPLYSTR)
	private Map<Long, DefaultApply> applys = new HashMap<>();
	
	@Column(value = PROP_MEMBERSTR, clazzType = DefaultMember.class)
	private Map<Long, IMember> members= new HashMap<>();

	public Family() {

	}
	
	public Family(long id) {
		this.id = id;
	}
	
	@Override
	public Object key() {
		return getId();
	}
	@Override
	public String keyColumn() {
		return PROP_ID;
	}

	public Map<Long, DefaultApply> getApplys() {
		return applys;
	}

	@Override
	public Map<Long, IMember> getMembers() {
		return members;
	}

	public static Family create(long id, String name) {
		Family family = new Family(id);
		family.setCreateTime(TimeUtil.now());
		family.setName(name);
		return family;
	}
	
	//====================一些逻辑==================
	/**
	 * 获取家族职位
	 * @return
	 */
	@Override
	public int getPosition(long playerId) {
		IMember member = getMembers().get(playerId);
		//	0:没有职位
		return member == null? 0 : member.getPosition();
	}
	
	/**
	 * 是否存在权限
	 * @return
	 */
	public boolean hasPrivilege(long playerId, long privilege) {
		int position = getPosition(playerId);
		long allPrivileges = FamilyPosition.getPosition(position).getPrivilege();
		if (StateUtils.check(privilege, allPrivileges)) {
			return true;
		}
		return false;
	}
	
	//=====================接口实现==================
	
	/**
	 * 实例化一个新成员, 加入成员列表
	 */
	@Override
	public IMember newMember(long memberId) {
		DefaultMember member = new DefaultMember(memberId);
		member.setPosition(FamilyPosition.NOMAL.getValue());
		this.members.put(memberId, member);
		return member;
	}
	
	/**
	 * 实例化一个新成员, 设置为leader, 加入成员列表
	 */
	@Override
	public IMember newLeader(long memberId) {
		DefaultMember member = new DefaultMember(memberId);
		member.setPosition(FamilyPosition.PATRIARCH.getValue());
		this.members.put(memberId, member);
		return member;
	}

}
