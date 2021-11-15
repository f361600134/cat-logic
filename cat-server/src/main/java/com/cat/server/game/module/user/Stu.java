package com.cat.server.game.module.user;

import java.util.HashMap;
import java.util.Map;

import com.cat.orm.core.annotation.PO;
import com.cat.server.game.module.user.domain.StuPo;

/**
* @author Jeremy
*/
@PO(name = "stu")
public class Stu extends StuPo {
	
//	public static final String ID = Stu.class.getSimpleName();
	
	private final Map<Integer, Integer> map = new HashMap<>();
	
	public Stu() {

	}
	
	public Stu(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static Stu create(String name) {
		Stu stu = new Stu();
		stu.setId(1);
		stu.setAge(1);
		stu.setName(name);
		return stu;
	}
	
	public Map<Integer, Integer> getMap() {
		return map;
	}

	@Override
	public String toString() {
		return "Stu [id=" + id + ", name=" + name + ", age=" + age + ", birthday=" + birthday + "]";
	}
	
}
