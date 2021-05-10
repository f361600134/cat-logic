package com.cat.server.game.module.family.domain;


import java.util.ArrayList;
import java.util.List;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.utils.TimeUtil;

/**
* @author Jeremy
*/
@PO(name = "family")
public class Family extends FamilyPo implements IPersistence{
	
	@Column(PROP_APPLYSTR)
	private List<Long> applys = new ArrayList<>();
	
	@Column(PROP_MEMBERSTR)
	private List<FamilyMember> members= new ArrayList<>();

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

	public List<Long> getApplys() {
		return applys;
	}

	public void setApplys(List<Long> applys) {
		this.applys = applys;
	}

	public List<FamilyMember> getMembers() {
		return members;
	}

	public void setMembers(List<FamilyMember> members) {
		this.members = members;
	}
	
	public static Family create(long id, String name) {
		Family family = new Family(id);
		family.setCreateTime(TimeUtil.now());
		family.setName(name);
		return family;
	}
	
}
