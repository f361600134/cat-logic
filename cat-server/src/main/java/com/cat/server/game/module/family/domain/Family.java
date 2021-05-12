package com.cat.server.game.module.family.domain;


import java.util.HashMap;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.orm.util.StateUtils;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.family.assist.FamilyPosition;
import com.cat.server.game.module.group.DefaultMember;
import com.cat.server.game.module.group.IGroup;
import com.cat.server.game.module.group.IMember;
import com.cat.server.utils.TimeUtil;

/**
* @author Jeremy
*/
@PO(name = "family")
public class Family extends FamilyPo implements IPersistence, IGroup{
	
	@Column(PROP_APPLYSTR)
	private Map<Long, FamilyApply> applys = new HashMap<>();
	
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
	
	public String keyColumn() {
		return PROP_ID;
	}

	public Map<Long, FamilyApply> getApplys() {
		return applys;
	}
	
	public Map<Long, IMember> getMembers() {
		return members;
	}

	public static Family create(long id, String name) {
		Family family = new Family(id);
		family.setCreateTime(TimeUtil.now());
		family.setName(name);
		return family;
	}
	
	//===========一些逻辑=========
	/**
	 * 获取家族职位
	 * @return
	 */
	public int getPosition(long playerId) {
		IMember member = getMembers().get(playerId);
		return member == null? 0 : member.getPosition();//	没有职位
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
	
}
