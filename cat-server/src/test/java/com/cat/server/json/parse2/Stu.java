package com.cat.server.json.parse2;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.cat.server.game.module.resource.domain.ResourceGroup;

public class Stu {

	private int id;
	private String name;
	private Map<Integer, Integer> map;
	private ResourceGroup group;
//	private long[][] data;

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

	public ResourceGroup getGroup() {
		return group;
	}

	public void setGroup(ResourceGroup group) {
		this.group = group;
	}
	
	public Map<Integer, Integer> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Integer> map) {
		this.map = map;
	}

//	public long[][] getData() {
//		return data;
//	}
//
//	public void setData(long[][] data) {
//		this.data = data;
//	}

	static Stu create() {
		Stu stu = new Stu();
		stu.setId(1);
		stu.setName("aa");
		
		ResourceGroup group = new ResourceGroup();
		group.addCount(1, 1);
		group.addCount(2, 2);
		stu.setGroup(group);
		
//		long [][]data = {{1001,2},{1002,2}};
//		stu.setData(data);
		
		return stu;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	


}