package com.cat.server.game.module.resource.parse;

import java.util.Map;

public class Stu {

	private int id;
	private String name;
	private Map<Integer, Integer> group;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public ResourceGroup getGroup() {
//		return group;
//	}
//
//	public void setGroup(ResourceGroup group) {
//		this.group = group;
//	}
	
	static Stu create() {
		Stu stu = new Stu();
		stu.setId(1);
		stu.setName("aa");
//		ResourceGroup group = new ResourceGroup();
//		group.addCount(1, 1);
//		group.addCount(2, 2);
//		stu.setGroup(group);
		return stu;
	}

	public Map<Integer, Integer> getGroup() {
		return group;
	}

	public void setGroup(Map<Integer, Integer> group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Stu [id=" + id + ", name=" + name + ", group=" + group + "]";
	}

}